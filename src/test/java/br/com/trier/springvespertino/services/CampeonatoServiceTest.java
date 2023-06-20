package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.service.CampeonatoService;
import jakarta.transaction.Transactional;

@Transactional
class CampeonatoServiceTest extends BaseTests {

    @Autowired
    CampeonatoService service;

    @Test
    @DisplayName("Buscar por id")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void findByIdTest() {
        Campeonato camp = service.findById(1);
        assertNotNull(camp);
        assertEquals(1, camp.getId());
        assertEquals("Modo Turbo", camp.getDescription());

    }

    @Test
    @DisplayName("Buscar por id inválido")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void findByIdInvalidTest() {
        assertNull(service.findById(25));
    }

    @Test
    @DisplayName("Buscar tudo")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void listAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Inserir novo campeonato")
    void insertTest() {
        Campeonato camp = new Campeonato(null, "Campeonato", 2024);
        service.insert(camp);
        assertEquals(1, service.listAll().size());
        assertEquals(1, camp.getId());
        assertEquals("Campeonato", camp.getDescription());
    }

    @Test
    @DisplayName("Atualizar campeonato")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
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
    @DisplayName("Deleta campeonato")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void deleteTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
        assertNull(service.findById(1));
    }

    @Test
    @DisplayName("Delete campeonato que não existe")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void deleteIdNoExistTest() {
        assertEquals(3, service.listAll().size());
        service.delete(10);
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Procura por ano")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void findByYearTest() {
        assertEquals(1, service.findByYear(2020).size());
        assertEquals(1, service.findByYear(2021).size());
        assertEquals(1, service.findByYear(2022).size());

    }

    @Test
    @DisplayName("Procura por ano entre")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void findByYearBetweenTest() {
        List<Campeonato> camp = service.findByYearBetween(2020, 2022);
        List<Campeonato> resultCamp = new ArrayList<>();
        resultCamp.add(new Campeonato(1, "Modo Turbo", 2020));
        resultCamp.add(new Campeonato(1, "Corrida de Rolimã", 2021));
        resultCamp.add(new Campeonato(1, "Super corrida dos 1.0", 2022));
        assertEquals(resultCamp.size(), camp.size());
        assertArrayEquals(camp.toArray(),resultCamp.toArray());
    }



}
