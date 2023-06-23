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
import br.com.trier.springvespertino.models.PilotoCorrida;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.PilotoCorridaService;
import br.com.trier.springvespertino.service.PilotoService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/piloto.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pista.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/corrida.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/piloto-corrida.sql")
public class PilotoCorridaTest extends BaseTests {

    @Autowired
    PilotoCorridaService service;

    @Autowired
    PilotoService pilotoService;

    @Autowired
    CorridaService corridaService;

    @Test
    @DisplayName("Teste buscar por id")
    void findByIdTest() {
        PilotoCorrida pilotoCorrida = service.findById(1);
        assertNotNull(pilotoCorrida);
        assertEquals(1, pilotoCorrida.getId());
        assertEquals(1, pilotoCorrida.getPosition());
        assertEquals(1, pilotoCorrida.getPiloto().getId());
        assertEquals(1, pilotoCorrida.getCorrida().getId());
    }

    @Test
    @DisplayName("Teste buscar por id inválido")
    void findIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Piloto/Corrida 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void findAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Test buscar todos com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    void searchAllWithNoPilotoCorridaTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum piloto/corrida encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste inserir novo piloto/corrida")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    @Sql({ "classpath:/resources/sqls/piloto.sql" })
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    @Sql({ "classpath:/resources/sqls/corrida.sql" })
    void insertTest() {
        PilotoCorrida piloto = new PilotoCorrida(null, pilotoService.findById(1), corridaService.findById(1), 1);
        service.insert(piloto);
        assertEquals(1, service.listAll().size());
        assertEquals(1, piloto.getId());
        assertEquals(1, piloto.getPosition());
    }

    @Test
    @DisplayName("Test update piloto_corrida")
    void updateTest() {
        PilotoCorrida piloto = service.findById(1);
        assertNotNull(piloto);
        assertEquals(1, piloto.getId());
        assertEquals(1, service.findById(1).getPosition());
        piloto = new PilotoCorrida(1, pilotoService.findById(1), corridaService.findById(1), 2);
        service.update(piloto);
        assertEquals(3, service.listAll().size());
        assertEquals(1, piloto.getId());
        assertEquals(2, service.findById(1).getPosition());
    }

    @Test
    @DisplayName("Teste update piloto/corrida não existente")
    void updateNonExistsTest() {
        PilotoCorrida piloto = new PilotoCorrida(10, pilotoService.findById(1), corridaService.findById(1), 1);
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(piloto));
        assertEquals("Piloto/Corrida 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste deletar piloto/corrida")
    void deleteTest() {
        PilotoCorrida piloto = service.findById(1);
        assertNotNull(piloto);
        assertEquals(1, piloto.getId());
        assertEquals(1, piloto.getPosition());
        service.delete(1);
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Piloto/Corrida 1 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Test deletat piloto não existente")
    void deleteNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("Piloto/Corrida 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste encontra piloto/corrida por colocação")
    void findByPositionTest() {
        List<PilotoCorrida> lista = service.findByPosition(1);
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste encontra piloto/corrida por colocação diferentes")
    void findByPositionNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByPosition(30));
        assertEquals("Nenhum piloto cadastrado na posição: 30", exception.getMessage());
    }
    

    @Test
    @DisplayName("Encontra piloto/corrida por piloto")
    void findByPilotoTest() {
        List<PilotoCorrida> lista = service.findByPilotoOrderByPosition(pilotoService.findById(1));
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste encontra piloto/corrida por piloto")
    void findByCorridaNoExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByCorridaOrderByPosition(corridaService.findById(30)));
        assertEquals("Corrida 30 não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Encontra piloto/corrida por corrida")
    void findByCorridaTest() {
        List<PilotoCorrida> lista = service.findByCorridaOrderByPosition(corridaService.findById(1));
        assertEquals(1, lista.size());
    }
    



}
