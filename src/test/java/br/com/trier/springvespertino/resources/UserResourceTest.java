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
import br.com.trier.springvespertino.config.jwt.LoginDTO;
import br.com.trier.springvespertino.models.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    @DisplayName("Teste obter Token")
    private HttpHeaders getHeaders(String email, String password) {
        LoginDTO loginDTO = new LoginDTO(email, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        String token = responseEntity.getBody();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }

    private ResponseEntity<UserDTO> getUser(String url) {
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders("email1@gmail.com", "senha1")),
                UserDTO.class);
    }


    private ResponseEntity<List<UserDTO>> getUsers(String url, HttpHeaders headers) {
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<UserDTO>>() {
        });
    }

    @Test
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste buscar por id")
    public void getIdTest() {
        ResponseEntity<UserDTO> response = getUser("/usuarios/4");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDTO user = response.getBody();
        assertEquals("User 1", user.getName());
    }

    @Test
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste buscar por id inexistente")
    public void getNotFoundTest() {
        ResponseEntity<UserDTO> response = getUser("/usuarios/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste cadastrar usuário")
    public void createUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
        HttpHeaders headers = getHeaders("email1@gmail.com", "senha1");
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios", HttpMethod.POST, requestEntity,
                UserDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        UserDTO user = responseEntity.getBody();
        assertEquals(dto.getName(), user.getName());
    }

    @Test
    @DisplayName("Teste deleta usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserTest() {
        ResponseEntity<Void> responseEntity = rest.exchange("/usuarios/4", HttpMethod.DELETE,
                new HttpEntity<>(getHeaders("email1@gmail.com", "senha1")), Void.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test deleta usuário inexistente")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserNonExistTest() {
        ResponseEntity<Void> responseEntity = rest.exchange("/usuarios/1000", HttpMethod.DELETE,
                new HttpEntity<>(getHeaders("email1@gmail.com", "senha1")), Void.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste Listar todos")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void listAllTest() {
        ResponseEntity<List<UserDTO>> responseEntity = getUsers("/usuarios", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<UserDTO> users = responseEntity.getBody();
        assertEquals(3, users.size());
    }

    @Test
    @DisplayName("Test alterar usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void updateTest() {
        UserDTO dto = new UserDTO(4, "nome atualizado", "email atualizado", "senhaNova", "ADMIN,USER");
        HttpHeaders headers = getHeaders("email1@gmail.com", "senha1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/4", HttpMethod.PUT, requestEntity,
                UserDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDTO user = responseEntity.getBody();
        assertEquals(dto.getName(), user.getName());
    }

    @Test
    @DisplayName("Teste atualiza usuario inexistente")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void testUpdateUserNonExistTest() {
        UserDTO dto = new UserDTO(100, "nomeNovo", "emailNovo", "senhaNova", "ADMIN");
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "senha1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/100", HttpMethod.PUT,
                new HttpEntity<>(dto, headers), UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste buscar por nome com ")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void findByNameStartsWithIgnoreCaseTest() {
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "senha1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<List<UserDTO>> response = rest.exchange("/usuarios/name/user", HttpMethod.GET,
                new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<UserDTO>>() {
                });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 3);
    }
}
