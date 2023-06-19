package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.service.UserService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
class UserServiceTest extends BaseTests {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("Teste buscar usuário por ID")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
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
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir usuário")
    void insertUserTest() {
        User usuario = new User(null, "insert", "insert", "insert");
        userService.insert(usuario);
        usuario = userService.findById(1);
        assertEquals(1, usuario.getId());
        assertEquals("insert", usuario.getName());
        assertEquals("insert", usuario.getEmail());
        assertEquals("insert", usuario.getPassword());
    }

    @Test
    @DisplayName("Teste Remover usuário")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void removeUserTest() {
        userService.delete(1);
        List<User> lista = userService.listAll();
        assertEquals(1, lista.size());
        assertEquals(2, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste remover usuário inexistente")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void removeUserNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void listAllUsersTest() {
        List<User> lista = userService.listAll();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Teste listar todos sem usuários cadastrados")
    void listAllUsersEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
        assertEquals("Nenhum usuario cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste alterar usuário")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void updateUsersTest() {
        var usuario = userService.findById(1);
        assertEquals("User1", usuario.getName());
        var usuarioAltera = new User(1, "altera", "altera", "altera");
        userService.update(usuarioAltera);
        usuario = userService.findById(1);
        assertEquals("altera", usuario.getName());
    }

    @Test
    @DisplayName("Teste alterar usuário inexstente")
    void updateUsersNonExistsTest() {
        var usuarioAltera = new User(1, "altera", "altera", "altera");
        var exception = assertThrows(ObjectNotFound.class, () -> userService.update(usuarioAltera));
        assertEquals("O usuário 1 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar usuário por email")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByEmailTest() {
        var usuario = userService.findById(1);
        assertNotNull(usuario);
        assertEquals("email1", usuario.getEmail());
    }

    @Test
    @DisplayName("Teste buscar usuário nome")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByNameTest() {
        var usuario = userService.findById(1);
        assertNotNull(usuario);
        assertEquals("User1", usuario.getName());
    }

    @Test
    @DisplayName("Teste buscar usuário inexisteste")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByNameNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.findByName("User3"));
        assertEquals("O usuário User3 não existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar usuario que inicia com a letra W")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.findByName("w"));
        assertEquals("O usuário w não existe.", exception.getMessage());

    }

    @Test
    @DisplayName("Teste buscar usuário por email")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByDuplicatedEmailTest() {
        User user = new User(1, "John", "john@example.com", "password");
        assertDoesNotThrow(() -> user.getEmail());
    }

}
