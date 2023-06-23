package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.service.EquipeService;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PilotoService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/piloto.sql")
public class PilotoServiceTest extends BaseTests {

    @Autowired
    PilotoService service;

    @Autowired
    PaisService paisService;

    @Autowired
    EquipeService equipeService;

    @Test
    @DisplayName("Teste buscar piloto por ID")
    void findByIdTest() {
        var piloto = service.findById(1);
        assertNotNull(piloto);
        assertEquals(1, piloto.getId());
        assertEquals("Renan", piloto.getName());

    }

    @Test
    @DisplayName("Teste buscar piloto por ID inexistente")
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Piloto 10 nao encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir piloto")
    void insertPilotoTest() {
        Piloto piloto = new Piloto(3, "Piloto Teste", paisService.findById(1), equipeService.findById(1));
        service.insert(piloto);
        assertEquals(3, service.listAll().size());
        assertEquals(3, piloto.getId());
        assertEquals("Piloto Teste", piloto.getName());
    }

    @Test
    @DisplayName("Teste inserir piloto nulo")
    void insertNullPilotoTest() {
        Piloto piloto = new Piloto(1, null, null, equipeService.findById(1));
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(piloto));
        assertEquals("Piloto nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Teste  update piloto")
    void updateTest() {
        Piloto piloto = service.findById(1);
        assertNotNull(piloto);
        assertEquals(1, piloto.getId());
        assertEquals("Renan", piloto.getName());
        piloto = new Piloto(1, "Updated Pilot", paisService.findById(1), equipeService.findById(1));
        service.update(piloto);
        assertEquals(3, service.listAll().size());
        assertEquals(1, piloto.getId());
        assertEquals("Updated Pilot", piloto.getName());
    }

    @Test
    @DisplayName("Teste  update piloto não existente")
    void updateInvalid() {
        Piloto piloto = new Piloto(10, "Invalid Pilot", paisService.findById(1), equipeService.findById(1));
        var ex = assertThrows(ObjectNotFound.class, () -> service.update(piloto));
        assertEquals("Piloto 10 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste Remover piloto")
    void removePilotoTest() {
        service.delete(1);
        List<Piloto> lista = service.listAll();
        assertEquals(2, lista.size());
        assertEquals(2, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste remover piloto inexistente")
    void removePilotoNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("Piloto 10 nao encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos pilotos")
    void listAllPilotosTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste listar todos sem nenhum cadastro")
    void listAllNoPilotoTest() {
        List<Piloto> lista = service.listAll();
        assertEquals(3, lista.size());
        service.delete(1);
        service.delete(2);
        service.delete(3);
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum piloto cadastrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste encontra pilotos por nome")
    void findByNameStartsWithIgnoreCase() {
        List<Piloto> lista = service.findByNameStartsWithIgnoreCase("R");
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Teste encontra pilotos por nome sem nomes iguais")
    void findByNameStartsWithIgnoreCaseInvalid() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameStartsWithIgnoreCase("Z"));
        assertEquals("Nenhum piloto com esse nome.", ex.getMessage());
    }

    @Test
    @DisplayName("Encontra pilotos por país")
    void findByPaisOrderByName() {
        List<Piloto> lista = service.findByPaisOrderByName(paisService.findById(1));
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Encontra pilotos por país sem nenhum com este país")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void findByCountryOrderByNameInvalid() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByPaisOrderByName(paisService.findById(1)));
        assertEquals("Nenhum piloto cadastrado no país: Brasil", ex.getMessage());
    }

    @Test
    @DisplayName("Teste ncontra pilotos por time")
    void findByEquipeOrderByName() {
        List<Piloto> lista = service.findByEquipeOrderByName(equipeService.findById(1));
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Encontra pilotos por time sem times encontrados")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void findByEquipeOrderByNameInvalid() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByEquipeOrderByName(equipeService.findById(1)));
        assertEquals("Nenhum piloto cadastrado na equipe: Willians", ex.getMessage());
    }

}