package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests {

    @Autowired
    PaisService service;

    @Test
    @DisplayName("Buscar por id")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void findByIdTest() {
        Pais pais = service.findById(1);
        assertNotNull(pais);
        assertEquals(1, pais.getId());
        assertEquals("Brasil", pais.getName());

    }

    @Test
    @DisplayName("Teste buscar por ID inexistente")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Pais 10 não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Buscar tudo")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void listAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Insere novo pais")
    void insertTest() {
        Pais pais = new Pais(null, "PaisNovo");
        service.insert(pais);
        assertEquals(1, service.listAll().size());
        assertEquals(1, pais.getId());
        assertEquals("PaisNovo", pais.getName());
    }

    @Test
    @DisplayName("Altera país")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void updateTest() {
        Pais pais = service.findById(1);
        assertNotNull(pais);
        assertEquals(1, pais.getId());
        assertEquals("Brasil", pais.getName());
        pais = new Pais(1, "Tubarao");
        service.update(pais);
        assertEquals(3, service.listAll().size());
        assertEquals(1, pais.getId());
        assertEquals("Tubarao", pais.getName());
    }

    @Test
    @DisplayName("Deleta país")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void deleteTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
        assertNull(service.findById(1));
    }

    @Test
    @DisplayName("Deleta país que não existe")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void deleteIdNoExistTest() {
        assertEquals(3, service.listAll().size());
        service.delete(10);
    }

    @Test
    @DisplayName("Procura por nome")
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void findByCountryTest() {
        assertEquals(1, service.findByNameStartsWithIgnoreCase("br").size());
        assertEquals(1, service.findByNameStartsWithIgnoreCase("eu").size());
        assertEquals(1, service.findByNameStartsWithIgnoreCase("it").size());
    }

}
