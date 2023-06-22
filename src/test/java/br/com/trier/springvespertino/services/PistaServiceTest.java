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
    @DisplayName("Teste listar todas as pistas")
    void listAllPistaTest() {
        List<Pista> lista = pistaService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Teste listar todas as pistas não cadastradas")
    void listAllPistaEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.listAll());
        assertEquals("Nenhuma pista cadastrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir pista")
    void insertPistaTest() {
        Pista pista = new Pista(null, "insert", 2000, null);
        pistaService.insert(pista);
        pista = pistaService.findById(1);
        assertEquals(1, pista.getId());
        assertEquals("insert", pista.getName());
        assertEquals(2000, pista.getSize());
        assertEquals(null, pista.getPais());
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
    @DisplayName("Teste nenhuma pista cadastrada para o país")
    void testFindByPaisOrderBySizeDescNoPistasCadastradas() {
        assertThrows(ObjectNotFound.class,
                () -> pistaService.findByPaisOrderBySizeDesc(paisService.findById(1)));
    }

    @Test
    @DisplayName("Teste procura pistas encontradas descrecente ")
    void testFindByPaisOrderBySizeDescPistasEncontradas() {
        pistaService.insert(new Pista(1, "Pista4", 1000, paisService.findById(1)));
        pistaService.insert(new Pista(2, "Pista5", 2000, paisService.findById(1)));
        List<Pista> list = pistaService.findByPaisOrderBySizeDesc(paisService.findById(1));
        assertEquals(2, list.size());
        assertEquals("Pista5", list.get(0).getName());
        assertEquals("Pista4", list.get(1).getName());
    }
    @Test
    @DisplayName("busca pais da pista retornando vazio")
    void findPistaByPaisEmptyListTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findByPaisOrderBySizeDesc(paisService.findById(100)));
        assertEquals("Pais 100 não encontrado", exception.getMessage());
    }
    

   
    
    
}
