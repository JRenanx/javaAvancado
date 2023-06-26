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
import br.com.trier.springvespertino.config.jwt.LoginDTO;
import br.com.trier.springvespertino.models.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {

    @Autowired
    protected TestRestTemplate rest;


    @SuppressWarnings("unused")
    private ResponseEntity<List<UserDTO>> getUsers(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
        });
    }
    
    
    @Test
    @DisplayName("Teste obter Token")
    @Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    public void getToken() {
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "senha1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange(
                "/auth/token", 
                HttpMethod.POST,  
                requestEntity,    
                String.class   
                );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************"+token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<List<UserDTO>> response =  rest.exchange("/usuarios", HttpMethod.GET, new HttpEntity<>(null, headers) ,new ParameterizedTypeReference<List<UserDTO>>() {});
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    private HttpHeaders  getHeader(String email, String senha) {
        LoginDTO loginDTO = new LoginDTO(email, senha);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange(
                "/auth/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        HttpHeaders headersR = new HttpHeaders();
        headersR.setBearerAuth(responseEntity.getBody());
        return headersR;
    }
    
    private ResponseEntity<UserDTO> getUser(String url){
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeader("email1@gmail.com", "senha1")), UserDTO.class);
    }
    

    @Test
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste cuscar por id")
    public void getIdTest() {
        ResponseEntity<UserDTO> response = getUser("/usuarios/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDTO user = response.getBody();
        assertEquals("User 1", user.getName());
    }

    @Test
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    @DisplayName("Teste buscar por id inexistente")
    public void getNotFoundTest() {
        ResponseEntity<UserDTO> response = getUser("/usuarios/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste cadastrar usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    public void createUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN,USER");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios", HttpMethod.POST, requestEntity,
                UserDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        UserDTO user = responseEntity.getBody();
        assertEquals("nome", user.getName());
    }

    @Test
    @DisplayName("Teste deleta usuário")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(n, headers);
        ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1", HttpMethod.DELETE, requestEntity,
                UserDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test deleta usuário inexistente")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void deleteUserNonExistTest() {
        ResponseEntity<UserDTO> response = getUser("/usuarios/100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste Listar todos")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    public void listAllTest() {
        ResponseEntity<List<UserDTO>> responseEntity = getUsers("/usuarios");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste atualiza usuario")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void updateTest() {
        Integer userId = 2;
        UserDTO updatedUserDTO = new UserDTO(userId, "novoNome", "novoEmail", "novaSenha", "ADMIN,USER");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(updatedUserDTO, headers);
        ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/" + userId, HttpMethod.PUT, requestEntity,
                UserDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserDTO updatedUser = responseEntity.getBody();
        assertNotNull(updatedUser);
        assertEquals(updatedUserDTO.getId(), updatedUser.getId());
        assertEquals(updatedUserDTO.getName(), updatedUser.getName());
        assertEquals(updatedUserDTO.getEmail(), updatedUser.getEmail());

    }

    @Test
    @DisplayName("Teste buscar por nome com ")
    @Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    public void findByNameStartsWithIgnoreCaseTest() {
        String name = "User";
        ResponseEntity<List<UserDTO>> responseEntity = rest.exchange("/usuarios/name/{name}", 
                HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<UserDTO>>(){}, name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UserDTO> users = responseEntity.getBody();
        assertNotNull(users);
        assertEquals(2, users.size());
        
        UserDTO user1 = users.get(0);
        assertEquals(1, user1.getId());
        assertEquals("User 1", user1.getName());
        assertEquals("email1@gmail.com", user1.getEmail());

        UserDTO user2 = users.get(1);
        assertEquals(2, user2.getId());
        assertEquals("User 2", user2.getName());
        assertEquals("email2@gmail.com", user2.getEmail());
    }
    }

