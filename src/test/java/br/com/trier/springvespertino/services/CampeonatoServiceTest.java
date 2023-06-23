package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
class CampeonatoServiceTest extends BaseTests {

    @Autowired
    CampeonatoService service;

    @Test
    @DisplayName("Teste buscar por id")
    void findByIdTest() {
        Campeonato camp = service.findById(1);
        assertNotNull(camp);
        assertEquals(1, camp.getId());
        assertEquals("Modo Turbo", camp.getDescription());

    }

    @Test
    @DisplayName("Teste buscar por id invalido")
    void findByIdInvalidTest() {
        assertNull(service.findById(25));
    }

    @Test
    @DisplayName("Teste listar todos")
    void listAllCampeonatoTest() {
        List<Campeonato> lista = service.listAll();
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem cadastro")
    void listAllNoCampeonatoTest() {
        List<Campeonato> lista = service.listAll();
        assertEquals(3, lista.size());
        service.delete(1);
        service.delete(2);
        service.delete(3);
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum campeonato cadastrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir campeonato")
    @Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
    void insertCampeonatoTest() {
        Campeonato camp = new Campeonato(null, "insert", 2005);
        service.insert(camp);
        assertEquals(1, service.listAll().size());
        assertEquals(1, camp.getId());
        assertEquals("insert", camp.getDescription());
        assertEquals(2005, camp.getYear());
    }
    
    @Test
    @DisplayName("Teste inserir campeonato < 1990")
    void insertCampeonatoInvalidYearTest() {
        Campeonato camp = new Campeonato (null, "insert", 1985);
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(camp));
        assertEquals("Campeonato deve estar estre os anos de 1990 e 2024.", exception.getMessage()); 
    }
    
    
    @Test
    @DisplayName("Teste atualizar campeonato")
    void updateTest() {
        Campeonato camp = service.findById(1);
        assertNotNull(camp);
        assertEquals(1, camp.getId());
        assertEquals("Modo Turbo", camp.getDescription());
        camp = new Campeonato(1, "Modo Turbo 2.0", 2024);
        service.update(camp);
        assertEquals(3, service.listAll().size());
        assertEquals(1, camp.getId());
        assertEquals("Modo Turbo 2.0", camp.getDescription());
        assertEquals(2024, camp.getYear());
    }

    @Test
    @DisplayName("Teste deleta campeonato")
    void deleteTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
        assertNull(service.findById(1));
    }

    @Test
    @DisplayName("Teste delete campeonato que não existe")
    void deleteIdNoExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("Campeonato 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Procura por ano")
    void findByYearTest() {
        assertEquals(1, service.findByYear(2020).size());
        assertEquals(1, service.findByYear(2021).size());
        assertEquals(1, service.findByYear(2022).size());

    }

    @Test
    @DisplayName("Teste procura por ano entre")
    void findByYearBetweenTest() {
            assertEquals(2, service.findByYearBetween(2021,2022).size());
            var ex = assertThrows(ObjectNotFound.class, () ->
            service.findByYearBetween(2009,2019));
            assertEquals("Nenhum campeonato entre 2009 e 2019", ex.getMessage());
        }
    
    @Test
    @DisplayName("Teste  procura por ano e descrição errada")
    void findByYearAndDescriptionWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByYearAndDescription(1990, 2005, "z"));
        assertEquals("Campeonato não encontrado.", exception.getMessage());
    }
    @Test
    @DisplayName("UTeste  ppdate campeonato passando ano inválido")
    void updateInvalidYearTest() { 
        Campeonato camp = service.findById(1);
        assertNotNull(camp);
        assertEquals(1, camp.getId());
        assertEquals("Modo Turbo", camp.getDescription());  
        var campAltera = new Campeonato(1, "Modo Turbo 2", 2030);
        var exception = assertThrows(IntegrityViolation.class, () -> 
        service.update(campAltera));
        assertEquals("Campeonato deve estar estre os anos de 1990 e 2024", exception.getMessage());  
    }
    
    @Test
    @DisplayName("Teste insere com ano do campeonato nulo")
    void insertCampeonatoWithNullYearTest() {
        Campeonato camp = new Campeonato(null, "insert", null);
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(camp));
        assertEquals("Ano não pode ser nulo.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar por ano de campeonato")
    void findByCampeonatoYearTest() {
        assertEquals(1, service.findByYear(2021).size());
    }
    
    @Test
    @DisplayName("Teste buscar por ano de campeonato nulo")
    void findByCampeonatoYearNullTest() {
        var ex = assertThrows(IntegrityViolation.class, () ->
        service.findByYear(1990));
        assertEquals("O campeonato deve estar ente 1990 e %s".formatted(LocalDateTime.now().plusYears(1).getYear()), ex.getMessage());

    }
    



}
