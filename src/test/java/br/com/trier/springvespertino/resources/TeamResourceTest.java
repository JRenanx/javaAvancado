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

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.models.Team;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<Team> getTeam(String url) {
        return rest.getForEntity(url, Team.class);
    }

    private ResponseEntity<List<Team>> getTeams(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Team>>() {
        });
    }

    @Test
    @DisplayName("Teste inserir equipe")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    public void insertEquipeTest() {
        Team dto = new Team(null, "Corsa Racers");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Team> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<Team> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity,
                Team.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Team equipe = responseEntity.getBody();
        assertEquals("Corsa Racers", equipe.getName());
    }

    @Test
    @DisplayName("Teste excluir equipe inexistente")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    public void deleteEquipeInexistTest() {
        ResponseEntity<Team> responseEntity = rest.exchange("/equipes/100",
                HttpMethod.DELETE, new HttpEntity<>(""),
                Team.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste buscar por id")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void findByIdTest() {
        ResponseEntity<Team> response = getTeam("/equipe/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Team equipe = response.getBody();
        assertEquals("Willians", equipe.getName());
    }

    @Test
    @DisplayName("Teste buscar por id inexistente")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void findByIdNonExistsTest() {
        ResponseEntity<Team> response = getTeam("/equipes/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste listar todos equipes")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void listAllTest() {
        ResponseEntity<List<Team>> response = getTeams("/equipe");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Teste alterar equipe")
    public void updateEquipeTest() {
        Integer equipeId = 1;
        Team updatedEquipeDTO = new Team(equipeId, "nova equipe");
        ResponseEntity<Team> responseEntity = rest.exchange("/equipes/" + equipeId, HttpMethod.PUT, new HttpEntity<>(updatedEquipeDTO), Team.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        Team updatedEquipe = responseEntity.getBody();
        assertNotNull(updatedEquipe);
        assertEquals(updatedEquipeDTO.getName(), updatedEquipe.getName());
    }

    @Test
    @DisplayName("Teste deletar equipe")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void deleteEquipeTest() {
        ResponseEntity<Void> responseEntity = rest.exchange("/equipe/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Teste buscar quipe por nome com ")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/equipe.sql" })
    public void findByNameStartsWithIgnoreCaseTest() {
        String name = "Ferrari";
        ResponseEntity<List<Team>> responseEntity = rest.exchange("/equipe/name/{name}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Team>>() {
                }, name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Team> equipes = responseEntity.getBody();
        assertNotNull(equipes);
        assertEquals(3, equipes.size());

        Team equipe1 = equipes.get(1);
        assertEquals(1, equipe1.getId());
        assertEquals("Willians", equipe1.getName());

        Team equipe2 = equipes.get(2);
        assertEquals(2, equipe2.getId());
        assertEquals("Ferrari", equipe2.getName());
    }

}