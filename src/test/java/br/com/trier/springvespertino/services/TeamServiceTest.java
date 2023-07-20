package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.service.TeamService;
import jakarta.transaction.Transactional;

@Transactional
public class TeamServiceTest extends BaseTests{

    @Autowired
    TeamService service;

    @Test
    @DisplayName("Buscar por id")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void findByIdTest() {
        Team equipe = service.findById(1);
        assertNotNull(equipe);
        assertEquals(1, equipe.getId());
        assertEquals("Willians", equipe.getName());

    }

    @Test
    @DisplayName("Buscar por id inválido")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void findByIdInvalidTest() {
        assertNull(service.findById(4));
    }

    @Test
    @DisplayName("Buscar todos")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void listAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Insert nova equipe")
    void insertTest() {
        Team equipe = new Team(null, "EquipeNova");
        service.insert(equipe);
        assertEquals(1, service.listAll().size());
        assertEquals(1, equipe.getId());
        assertEquals("EquipeNova", equipe.getName());
    }

    @Test
    @DisplayName("Update equipe")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void updateTest() {
        Team equipe = service.findById(1);
        assertNotNull(equipe);
        assertEquals(1, equipe.getId());
        assertEquals("Willians", equipe.getName());
        equipe = new Team(1, "Willians 2.0");
        service.update(equipe);
        assertEquals(3, service.listAll().size());
        assertEquals(1, equipe.getId());
        assertEquals("Willians 2.0", equipe.getName());
    }

    @Test
    @DisplayName("Delete equipe")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void deleteTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
        assertEquals(2, service.listAll().get(0).getId());
    }

    @Test
    @DisplayName("Delete equipe que não existe")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void deleteIdNoExistTest() {
        assertEquals(3, service.listAll().size());
        service.delete(10);
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Procura por nome")
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    void findByTeamTest() {
        assertEquals(1, service.findByNameStartsWithIgnoreCase("willians").size());
        assertEquals(1, service.findByNameStartsWithIgnoreCase("ferrari").size());
        assertEquals(1, service.findByNameStartsWithIgnoreCase("redbull").size());
    }

}
