package br.com.trier.springvespertino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import br.com.trier.springvespertino.models.dto.EquipeDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<EquipeDTO> getTeam(String url) {
        return rest.getForEntity(url, EquipeDTO.class);
    }

    private ResponseEntity<List<EquipeDTO>> getTeams(String url) {
        return rest.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<EquipeDTO>>() {}
        );
    }
    
    @Test
    @DisplayName("Teste inserir time")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    public void insertEquipeTest() {
        EquipeDTO dto = new EquipeDTO(null, "Corsa Racers");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<EquipeDTO> responseEntity = rest.exchange(
                "/equipe", 
                HttpMethod.POST,  
                requestEntity,    
                EquipeDTO.class   
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        EquipeDTO equipe = responseEntity.getBody();
        assertEquals("Corsa Racers", equipe.getName());
    }
    
    @Test
    @DisplayName("Teste buscar por id")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void findByIdTest() {
        ResponseEntity<EquipeDTO> response = getTeam("/equipe/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        EquipeDTO equipe = response.getBody();
        assertEquals("Willians", equipe.getName());
    }

    @Test
    @DisplayName("Teste buscar por id inexistente")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void findByIdNonExistsTest() {
        ResponseEntity<EquipeDTO> response = getTeam("/equipe/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Teste listar todos equipes")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void listAllTest() {
        ResponseEntity<List<EquipeDTO>> response = getTeams("/equipe"); 
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    @DisplayName("Teste alterar equipe")
    public void updateEquipeTest() {
        EquipeDTO dto = new EquipeDTO(null, "TesteUpdate");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe/1",HttpMethod.PUT,requestEntity,EquipeDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        EquipeDTO equipe = responseEntity.getBody();
        assertEquals("TesteUpdate", equipe.getName());
        assertEquals(1, equipe.getId());
    }
    
    @Test
    @DisplayName("Teste deletar equipe")
    @Sql("classpath:/resources/sqls/limpa_tabelas.sql")
    @Sql("classpath:/resources/sqls/equipe.sql")
    public void deleteEquipeTest() {
        ResponseEntity<Void> responseEntity = rest.exchange("/equipe/1", HttpMethod.DELETE,null,Void.class);      
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }


    
}