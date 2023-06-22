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
import br.com.trier.springvespertino.models.Equipe;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.service.PilotoService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/piloto.sql")
public class PilotoServiceTest  extends BaseTests{

    @Autowired
    PilotoService pilotoService;
    
    Pais pais;
    Equipe equipe;
    
    
    @Test
    @DisplayName("Teste buscar piloto por ID")
    void findByIdTest() {
        var piloto = pilotoService.findById(1);
        assertNotNull(piloto);
        assertEquals(1, piloto.getId());
        assertEquals("Rubinho", piloto.getName());

    }
    
    @Test
    @DisplayName("Teste buscar por ID inexistente")
    void findIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotoService.findById(10));
        assertEquals("Piloto 10 nao encontrado.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar todos")
    void listAllTest() {
        List<Piloto> lista = pilotoService.listAll();
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem possuir usuários cadastrados")
    void listAllUsersEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotoService.listAll());
        assertEquals("Nenhum piloto cadastrado.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste inserir piloto")
    void insertPilotoTest() {
        Piloto piloto = new Piloto (null, "insert", pais, equipe);
        pilotoService.insert(piloto);
        piloto = pilotoService.findById(1);
        assertEquals(1, piloto.getId());
        assertEquals("insert", piloto.getName());
    }
    
    @Test
    @DisplayName("Teste alterar piloto")
    void updatePilotoTest() {
        var piloto = pilotoService.findById(1);
        assertEquals("Rubinho", piloto.getName());
        var pilotoAltera = new Piloto(1, "altera", pais, equipe);
        pilotoService.update(pilotoAltera);
        piloto = pilotoService.findById(1);
        assertEquals("altera", piloto.getName());
    }
    
    @Test
    @DisplayName("Update piloto não existente")
    void updateInvalid() {
        Piloto piloto = new Piloto(10, " Piloto Novo",pais, equipe );
        var ex = assertThrows(ObjectNotFound.class, () -> pilotoService.update(piloto));
        assertEquals("Piloto 10 nao encontrado.", ex.getMessage());
    }

    
    @Test
    @DisplayName("Teste remover piloto")
    void removePilotoTest() {
        pilotoService.delete(1);
        List<Piloto> lista = pilotoService.listAll();
        assertEquals(2, lista.size());
        assertEquals(2, lista.get(0).getId());
    }
    
      
    @Test
    @DisplayName("Teste buscar piloto por nome")
    void findByNameTest() {
        var piloto = pilotoService.findByNameStartsWithIgnoreCase("Ru");
        assertNotNull(piloto);
        assertEquals(1, piloto.size());
        var piloto2 = pilotoService.findByNameStartsWithIgnoreCase("Massa");
        assertEquals(1, piloto2.size());
    }
    
    @Test
    @DisplayName("Teste buscar piloto por nome errado")
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotoService.findByNameStartsWithIgnoreCase("w"));
        assertEquals("Nenhum piloto com esse nome.", exception.getMessage());

    }
    
    @Test
    @DisplayName("Teste buscar piloto por nome")
    void findByNameContainsTest() {
        var piloto = pilotoService.findByNameContainingIgnoreCase("Ru");
        assertNotNull(piloto);
        assertEquals(1, piloto.size());
    }
    
    @Test
    @Sql({ "classpath:/resources/sqls/piloto.sql" })
    void findByNameContaisInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotoService.findByNameContainingIgnoreCase("wi"));
        assertEquals("Nenhum piloto com esse nome.", exception.getMessage());

    }


}
