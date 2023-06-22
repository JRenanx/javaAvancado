package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import br.com.trier.springvespertino.utils.DataUtils;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests {

    @Autowired
    CorridaService service;

    @Autowired
    PistaService pistaService;

    @Autowired
    CampeonatoService campeonatoService;

    @Test
    @DisplayName("Teste buscar por ID válido")
    void findIdValidTest() {
        Corrida corrida = service.findById(1);
        assertNotNull(corrida);
        assertEquals(1, corrida.getId());
        assertEquals(2021, corrida.getDate().getYear());
    }

    @Test
    @DisplayName("Teste buscar por ID inválido")
    void findIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Corrida 10 não encontrada.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void searchAllTest() {
        List<Corrida> lista = service.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Test buscar com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    void findAllWithNoRaceTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhuma corrida cadastrada.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste inserir nova corrida")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void insertTest() {
        Corrida corrida = new Corrida(null, DataUtils.dateBrToZoneDate("10/01/2020"), pistaService.findById(1),
                campeonatoService.findById(1));
        service.insert(corrida);
        assertEquals(1, service.listAll().size());
        assertEquals(1, corrida.getId());
        assertEquals(2021, corrida.getDate().getYear());
    }

    @Test
    @DisplayName("Teste update corrida")
    void updateTest() {
        Corrida corrida = new Corrida(1, DataUtils.dateBrToZoneDate("01/10/2021"), pistaService.findById(2),
                campeonatoService.findById(1));
        service.update(corrida);
        assertEquals(3, service.listAll().size());
        assertEquals(1, corrida.getId());
        assertEquals(2021, corrida.getDate().getYear());
    }

    @Test
    @DisplayName("Test update corrida não existente")
    void updateInvalidTest() {
        Corrida corrida = new Corrida(10, ZonedDateTime.now(), pistaService.findById(2), campeonatoService.findById(1));
        var ex = assertThrows(ObjectNotFound.class, () -> service.update(corrida));
        assertEquals("A corrida 10 não existe", ex.getMessage());
    }

    @Test
    @DisplayName("Teste delete corrida")
    void deleteTest() {
        service.delete(1);
        List<Corrida> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(2, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste delete corrida não existente")
    void deleteInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("Corrida 10 não encontrada.", ex.getMessage());
    }

}
