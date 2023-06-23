package br.com.trier.springvespertino.services;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.util.List;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import br.com.trier.springvespertino.utils.DataUtils;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pista.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/corrida.sql")
public class CorridaServiceTest extends BaseTests {
    
    @Autowired
    CorridaService service;
    
    @Autowired
    CampeonatoService campeonatoService;
    
    @Autowired
    PistaService pistaService;
    
    Pista pista;
    Campeonato campeonato;
    

    
    @Test
    @DisplayName("Teste buscar corrida por ID")
    void findByIdTest() {
        var corrida = service.findById(1);
        assertNotNull(corrida);
        assertEquals(1, corrida.getId());
    }
    
    @Test
    @DisplayName("Teste buscar piloto por ID inexistente")
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Corrida 10 não encontrada.", exception.getMessage()); 
    }
    
    @Test
    @DisplayName("Teste inserir corrida")
    void insertPilotoTest() {
        String dataStr = "03/12/2018";
        ZonedDateTime data = DataUtils.dateBrToZoneDate(dataStr);
        Corrida corrida = new Corrida(1, data, pista, campeonato);
        service.insert(corrida);
        assertEquals(3, service.listAll().size());
        assertEquals(1, corrida.getId());
        assertEquals(data, corrida.getDate());
    }
    
    @Test
    @DisplayName("Teste Remover corrida")
    void removeCorridaTest() {
        service.delete(1);
        List<Corrida> lista = service.listAll();
        assertEquals(2, lista.size());
        assertEquals(2, lista.get(0).getId());
    }
    
    @Test
    @DisplayName("Teste remover corrida inexistente")
    void removeCorridaNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("Corrida 10 não encontrada.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste listar todas corridas cadastradas")
    void listAllCorridaTest() {
        List<Corrida> lista = service.listAll();
        assertEquals(3, lista.size());  
    }
    
    @Test
    @DisplayName("Teste listar todos sem nenhum cadastro")
    void listAllNoCorridaTest() {
        List<Corrida> lista = service.listAll();
        assertEquals(3, lista.size());
        service.delete(1);
        service.delete(2);
        service.delete(3);
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhuma corrida cadastrada.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste alterar corrida")
    void updateCorridaTest() {
        Corrida corrida = new Corrida(1, DataUtils.dateBrToZoneDate("03/12/2018"), pistaService.findById(2), campeonatoService.findById(1));
        service.update(corrida);
        assertEquals(3, service.listAll().size());
        assertEquals(1, corrida.getId());
        assertEquals(2018, corrida.getDate().getYear());
    }
    
    @Test
    @DisplayName("Teste buscar por ano da corrida")
    void findByDateCorridaTest() {
        List<Corrida> lista = service.findByDate(DataUtils.dateBrToZoneDate("10/01/2021"));
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste buscar por ano inexistente da corrida")
    void findByDateCorridaNoExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByDate(DataUtils.dateBrToZoneDate("03/10/2010")));
        assertEquals("Corrida não encontrada encontrada.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar entre anos da corrida")
    void findByDateBetweenCorridaTest() {
        List<Corrida> lista = service.findByDateBetween(DataUtils.dateBrToZoneDate("10/01/2021"), DataUtils.dateBrToZoneDate("20/03/2023"));
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste buscar entre anos inexistentes da corrida")
    void findByDateBetweenCorridaNoExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByDateBetween(DataUtils.dateBrToZoneDate("03/10/2000"), DataUtils.dateBrToZoneDate("03/12/2005")));
        assertEquals("Nenhuma corrida foi encontrada entre a data selecionada", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar pistas ordenadas da corrida")
    void findByPistaOrderByDateTest () {
        Pista pista1 = new Pista(1, "Pista 1", 2000, null);
        pistaService.insert(pista1);    
        List<Corrida> lista = service.findByPistaOrderByDate(pista1);
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste buscar pistas ordenadas da corrida inexistente")
    void findByPistaOrderByDateInexistTest () {
        Pista pistaInexistente = new Pista(999, "Pista Inexistente", 5000, null);
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByPistaOrderByDate(pistaInexistente));
        assertEquals("Nenhuma corrida cadastrada na pista: Pista Inexistente", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar campeonatos ordenados da corrida")
    void findByCampeonatoOrderByDateTest () {
        Campeonato campeonato = new Campeonato(1, "Nevile", 2018);
        campeonatoService.insert(campeonato);       
        List<Corrida> lista = service.findByCampeonatoOrderByDate(campeonato);
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste buscar pistas ordenadas da corrida inexistente")
    void findByCampeonatoOrderByDateNoExistTest () {
        Campeonato campeonatoInexistente = new Campeonato(999, "Campeonato Inexistente", 3000);
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByCampeonatoOrderByDate(campeonatoInexistente));
        assertEquals("Nenhuma corrida cadastrada no campeonato: Campeonato Inexistente", exception.getMessage());
    }
}