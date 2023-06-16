package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.service.UserService;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Teste buscar por usuário por ID")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void findByIdTest() {
        var usuario = userService.findById(1);
        assertNotNull(usuario);
        assertEquals(1, usuario.getId());
        assertEquals("User1", usuario.getName());
        assertEquals("email1", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
    }
    
    @Test
    @DisplayName("Teste buscar usuário por ID inexistente")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void findByIdWrongTest() {
        var usuario = userService.findById(1);
        assertNotNull(usuario);
    }
    
    @Test
    @DisplayName("Teste buscar usuario por nome")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void findByNameTest() {
        var usuario = userService.findByName("User");
        assertNotNull(usuario);
        assertEquals(2, usuario.size());
        var usuario1 = userService.findByName("User1");
        assertEquals(1, usuario1.size());
    }
    
    @Test
    @DisplayName("Teste buscar usuario por nome errado")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void findByNameWrongTest() {
        var usuario = userService.findByName("c");
        assertEquals(0, usuario.size());
    }
    
    @Test
    @DisplayName("Teste inserir usuario")
    void insertUserTes() {
        User usuario = new User(null, "nome", "Email", "senha");
        userService.insert(usuario);
        usuario = userService.findById(1);
        assertEquals(1, usuario.getId());
        assertEquals("nome", usuario.getName());
        assertEquals("Email", usuario.getEmail());
        assertEquals("senha", usuario.getPassword());
    }
    
    @Test
    @DisplayName("Teste apagar usuario por id")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void deleteByIdTest() {
        userService.delete(1);
        List<User> list = userService.listAll();
        assertEquals(1, list.size());
    }
    
    @Test
    @DisplayName("Teste apagar usuario por id incorreto")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void deleteByIdNonExistsTest() {
        userService.delete(10);
        List<User> list = userService.listAll();
        assertEquals(2, list.size());
    }
    
    @Test
    @DisplayName("Teste alterar usuario")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void updateByIdTest() {
        userService.delete(10);
        List<User> list = userService.listAll();
        assertEquals(2, list.size());
    }

}
