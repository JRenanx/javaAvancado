package br.com.trier.springvespertino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.models.Championship;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChampionshipResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<Championship> getCampeonato(String url) {
        return rest.getForEntity(url, Championship.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<Championship>> getCampeonatos(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Championship>>() {
        });
    }

    @Test
    @DisplayName("Teste buscar por id")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/campeonato.sql")
    public void findByIdTest() {
        ResponseEntity<Championship> response = rest.getForEntity("/campeonato/1", Championship.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Championship camp = response.getBody();
        assertNotNull(camp);
        assertEquals("Modo Turbo", camp.getDescription());
    }

    @Test
    @DisplayName("Teste buscar por id inexistente")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/campeonato.sql")
    public void findByIdNonExistsTest() {
        ResponseEntity<Championship> response = getCampeonato("/championship/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste inserir campeonato")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    public void insertCampeonatoTest() {
        Championship dto = new Championship(null, "Corrida de Saco", 2023);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Championship> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<Championship> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity,
                Championship.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Championship camp = responseEntity.getBody();
        assertEquals("Corrida de Saco", camp.getDescription());
    }

    @Test
    @DisplayName("Teste inserir campeonato invalido")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    public void insertCampeonatoWrongYearTest() {
        Championship camp = new Championship(null, "campeonato", 1965);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Championship> requestEntity = new HttpEntity<>(camp, headers);
        ResponseEntity<Championship> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity,
                Championship.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    @DisplayName("Teste excluir campeonato")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/campeonato.sql")
    public void testDeleteCampeonato() {
        ResponseEntity<Void> response = rest.exchange("/campeonato/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    @DisplayName("Teste listar todos os campeonatos")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/campeonato.sql")
    public void listAllTest() {
        ResponseEntity<List<Championship>> response = rest.exchange("/campeonato", HttpMethod.GET, null, new ParameterizedTypeReference<List<Championship>>() {});
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}
