package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pista.sql")
public class PistaServiceTest extends BaseTests {

    @Autowired
    PistaService pistaService;

    @Autowired
    PaisService paisService;

    @Test
    @DisplayName("Teste buscar pista por ID")
    void findByIdTest() {
        var pista = pistaService.findById(1);
        assertNotNull(pista);
        assertEquals(1, pista.getId());
        assertEquals("Pista", pista.getName());
    }

    @Test
    @DisplayName("Teste buscar pista por ID inexistente")
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findById(10));
        assertEquals("Pista 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todas pistas com cadastro")
    void listAllPistaTest() {
        List<Pista> lista = pistaService.listAll();
        assertEquals(3, lista.size());  
    }
    
    @Test
    @DisplayName("Teste listar todas sem nenhum cadastro")
    void listAllNoPistaTest() {
        List<Pista> lista = pistaService.listAll();
        assertEquals(3, lista.size());
        pistaService.delete(1);
        pistaService.delete(2);
        pistaService.delete(3);
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.listAll());
        assertEquals("Nenhuma pista cadastrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir pista")
    @Sql ({"classpath:/resources/sqls/limpa_tabelas.sql"})
    void insertPistaTest() {
        Pista pista = new Pista(4, "Pista da HotWhells", 6000, null);
        pistaService.insert(pista);
        pista = pistaService.findById(1);
        assertEquals(1, pista.getId());
        assertEquals("Pista da HotWhells", pista.getName());
        assertEquals(6000, pista.getSize());
    }

    @Test
    @DisplayName("Teste remover pista")
    void removePistaTest() {
        pistaService.delete(1);
        List<Pista> lista = pistaService.listAll();
        assertEquals(2, lista.size());
        assertEquals(2, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste alterar pista")
    void updatePistaTest() {
        var pista = pistaService.findById(1);
        assertEquals("Pista", pista.getName());
        var pistaAltera = new Pista(1, "Pista 500", 3000, null);
        pistaService.update(pistaAltera);
        pista = pistaService.findById(1);
        assertEquals("Pista 500", pista.getName());
    }

    @Test
    @DisplayName("Teste buscar pista por nome")
    void findByNameTest() {
        var pista = pistaService.findByNameStartsWithIgnoreCase("Pi");
        assertNotNull(pista);
        assertEquals(1, pista.size());
        var pista2 = pistaService.findByNameStartsWithIgnoreCase("Rampa");
        assertEquals(1, pista2.size());
    }

    @Test
    @DisplayName("Teste buscar Pista por nome errado")
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findByNameStartsWithIgnoreCase("w"));
        assertEquals("Nenhuma pista cadastrada com esse nome.", exception.getMessage());

    }

    @Test
    @DisplayName("Teste procura por tamanho entre")
    void findBySizeBetweenTest() {
        List<Pista> result = pistaService.findBySizeBetween(1000, 3000);
        List<Pista> pistaList = new ArrayList<>();
        pistaList.add(new Pista(1, "Pista", 1000, null));
        pistaList.add(new Pista(2, "Indy", 2000, null));
        pistaList.add(new Pista(3, "Rampa", 3000, null));
        Assertions.assertEquals(3, result.size());
        Assertions.assertArrayEquals(pistaList.toArray(), result.toArray());
    }

    @Test
    @DisplayName("Teste procura por tamanho invalido")
    void findBySizeBetweenInvalidFoundTest() {
        List<Pista> pistaList = new ArrayList<>();
        pistaList.add(new Pista(1, "Pista", 500, null));
        pistaList.add(new Pista(2, "Indy", 200, null));
        pistaList.add(new Pista(3, "Rampa", 800, null));
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findBySizeBetween(null, null));
        assertEquals("Nenhuma pista cadastrada com essas medidas.", exception.getMessage());

    }

    @Test
    @DisplayName("Teste inserir pista de tamanho inválido")
    void testInsertPistaInvalidSizeTest() {
        Pista pista = new Pista(null, "insert", -2000, null);
        Assertions.assertThrows(IntegrityViolation.class, () -> {
            pistaService.insert(pista);
        });
    }

 
    @Test
    @DisplayName("Teste busca pais da pista retornando vazio")
    void findPistaByPaisEmptyListTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findByPaisOrderBySizeDesc(paisService.findById(100)));
        assertEquals("Pais 100 não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar por tamanho de pista por pais em ordem")
    void findByPaisOrderBySizeTest() {
        List<Pista> lista = pistaService.findByPaisOrderBySizeDesc(paisService.findById(1));
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste buscar por tamanho de pista por pais em ordem (sem pista)")
    @Sql ({"classpath:/resources/sqls/limpa_tabelas.sql"})
    @Sql({"classpath:/resources/sqls/pais.sql"})
    void findByPaisOrderEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findByPaisOrderBySizeDesc(paisService.findById(1)));
        assertEquals("Nenhuma pista cadastrada no país: Brasil", exception.getMessage());
    }
    
   
    
    
}
