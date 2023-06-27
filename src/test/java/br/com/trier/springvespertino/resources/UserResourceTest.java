package br.com.trier.springvespertino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("Teste obter Token")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void getToken() {
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
        ResponseEntity<List<UserDTO>> response = rest.exchange("/usuarios", HttpMethod.GET,
                new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<UserDTO>>() {
                });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste buscar por id")
    public void getIdTest() {
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/3", HttpMethod.GET, new HttpEntity<>(null, headers),
                UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getName(), "User 1");
    }

    @Test
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste buscar por id inexistente")
    public void getNotFoundTest() {
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/10", HttpMethod.GET,
                new HttpEntity<>(null, headers), UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste cadastrar usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void createUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "USER");
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios", HttpMethod.POST, new HttpEntity<>(dto, headers),
                UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getName(), "nome");

    }

    @Test
    @DisplayName("Teste deleta usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserTest() {
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/3", HttpMethod.DELETE,
                new HttpEntity<>(null, headers), UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test deleta usuário inexistente")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserNonExistTest() {
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/100", HttpMethod.DELETE,
                new HttpEntity<>(null, headers), UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste Listar todos")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void listAllTest() {
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
        ResponseEntity<List<UserDTO>> response = rest.exchange("/usuarios", HttpMethod.GET,
                new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<UserDTO>>() {
                });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 3);
        assertEquals(response.getBody().get(0).getName(), "User 1");
    }

    @Test
    @DisplayName("Teste atualiza usuario")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void updateTest() {
        UserDTO dto = new UserDTO(1, "nomeNovo", "emailNovo", "senhaNova", "ADMIN");
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
        ResponseEntity<UserDTO> response = rest.exchange("/usuarios/5", HttpMethod.PUT, new HttpEntity<>(dto, headers),
                UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
                assertEquals(response.getBody().getName(), "nomeNovo");
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
