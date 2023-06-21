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

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests {

    @Autowired
    PistaService pistaService;

    @Test
    @DisplayName("Teste buscar pista por ID")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void findByIdTest() {
        var pista = pistaService.findById(1);
        assertNotNull(pista);
        assertEquals(1, pista.getId());
        assertEquals("Pista", pista.getName());
    }

    @Test
    @DisplayName("Teste buscar pista por ID inexistente")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findById(10));
        assertEquals("Pista 10 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todas as pistas")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void listAllPistaTest() {
        List<Pista> lista = pistaService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Teste listar todas as pistas  não cadastradas")
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
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void removePistaTest() {
        pistaService.delete(1);
        List<Pista> lista = pistaService.listAll();
        assertEquals(2, lista.size());
        assertEquals(2, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste alterar pista")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void updateUsersTest() {
        var pista = pistaService.findById(1);
        assertEquals("Pista", pista.getName());
        var pistaAltera = new Pista(1, "Pista 500", 3000, null);
        pistaService.update(pistaAltera);
        pista = pistaService.findById(1);
        assertEquals("Pista 500", pista.getName());
    }

    @Test
    @DisplayName("Teste buscar pista por nome")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void findByNameTest() {
        var pista = pistaService.findByNameStartsWithIgnoreCase("Pi");
        assertNotNull(pista);
        assertEquals(1, pista.size());
        var pista2 = pistaService.findByNameStartsWithIgnoreCase("Rampa");
        assertEquals(1, pista2.size());
    }

    @Test
    @DisplayName("Teste buscar Pista por nome errado")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pistaService.findByNameStartsWithIgnoreCase("w"));
        assertEquals("Nenhuma pista cadastrada com esse nome.", exception.getMessage());

    }

    @Test
    @DisplayName("Teste procura por tamanho entre")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
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
    @Sql({ "classpath:/resources/sqls/pista.sql" })
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
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    void testInsertPistaInvalidSizeTest() {
        Pista pista = new Pista(null, "insert", -2000, null);
        Assertions.assertThrows(IntegrityViolation.class, () -> {
            pistaService.insert(pista);
        });
    }

    @Test
    @DisplayName("Teste nenhuma pista cadastrada no país")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void testFindByPaisOrderBySizeDescNoPistasCadastradas() {
        Pais pais = new Pais(null, "Brasil");
        Assertions.assertThrows(ObjectNotFound.class, () -> pistaService.findByPaisOrderBySizeDesc(pais));
    }

    @Test
    @DisplayName("Teste procura pistas encontradas descrecente ")
    @Sql({ "classpath:/resources/sqls/pista.sql" })
    @Sql({ "classpath:/resources/sqls/pais.sql" })
    void testFindByPaisOrderBySizeDescPistasEncontradas() {
        Pais pais = new Pais(1, "Brasil");
        pais = new Pais(2, "Eua");
        List<Pais> listPais = new ArrayList<>(); 
        List<Pista> pistaList = new ArrayList<>();
        listPais.add(new Pais(1, "Brasil"));
        pistaList.add(new Pista(1, "Pista", 1000, null));
        List<Pista> result = pistaService.findByPaisOrderBySizeDesc(pais);
        Assertions.assertEquals(2, result.size());
    }
}
