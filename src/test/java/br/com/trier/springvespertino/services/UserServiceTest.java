package br.com.trier.springvespertino.services;

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
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
class UserServiceTest extends BaseTests {

    @Autowired
    UserService service;

    @Test
    @DisplayName("Teste buscar usuário por ID")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByIdTest() {
<<<<<<< HEAD
        var usuario = service.findById(3);
        assertNotNull(usuario);
        assertEquals(3, usuario.getId());
=======
        var usuario = userService.findById(4);
        assertNotNull(usuario);
        assertEquals(4, usuario.getId());
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
        assertEquals("User 1", usuario.getName());
        assertEquals("email1@gmail.com", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
    }

    @Test
    @DisplayName("Teste buscar usuário por ID inexistente")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void listAllUsersTest() {
<<<<<<< HEAD
        List<User> lista = service.listAll();
=======
        List<User> lista = userService.listAll();
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Teste inserir usuário")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void insertUserTest() {
        User usuario = new User(null, "User 1", "insert@gmail.com", "senha1", "ADMIN,USER");
        service.insert(usuario);
        assertEquals(3, service.listAll().size());
        assertEquals(3, usuario.getId());
        assertEquals("User 1", usuario.getName());
        assertEquals("email1@gmail.com", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
    }

    @Test
    @DisplayName("Teste remover usuário")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void removeUserTest() {
<<<<<<< HEAD
        service.delete(3);
        List<User> lista = service.listAll();
        assertEquals(2, lista.size());
        assertEquals(4, lista.get(0).getId());
=======
        userService.delete(4);
        List<User> lista = userService.listAll();
        assertEquals(2, lista.size());
        assertEquals(5, lista.get(0).getId());
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    }

    @Test
    @DisplayName("Teste remover usuário inexistente")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void removeUserNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos sem possuir usuários cadastrados")
    void listAllUsersEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum usuário cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste alterar usuário")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void updateUsersTest() {
<<<<<<< HEAD
        User usuario = service.findById(3);
        assertNotNull(usuario);
        assertEquals(3, usuario.getId());
        assertEquals("User 1", usuario.getName());
        assertEquals("email1@gmail.com", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
        usuario = new User(3, "User 1", "email1@gmail.com", "senha1", "ADMIN");
        service.update(usuario);
        assertEquals(3, service.listAll().size());
        assertEquals(3, usuario.getId());
        assertEquals("User 1", usuario.getName());
        assertEquals("email1@gmail.com", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
=======
        var usuario = userService.findById(4);
        assertEquals("User 1", usuario.getName());
        var usuarioAltera = new User(4, "altera", "altera", "altera", "ADMIN");
        userService.update(usuarioAltera);
        usuario = userService.findById(4);
        assertEquals("altera", usuario.getName());
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    }

    @Test
    @DisplayName("Teste alterar usuário inexistente")
    void updateUsersNonExistsTest() {
        User user = new User(1, "User80", "User80", "123", "ADMIN");
        var ex = assertThrows(ObjectNotFound.class, () -> service.update(user));
        assertEquals("O usuário 1 não existe", ex.getMessage());

    }

    @Test
    @DisplayName("Teste inserir usuário com email duplicado")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void insertEmailDuplicatedTest() {
        User user = new User(null, "User80", "email1@gmail.com", "123", "ADMIN");
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(user));
        assertEquals("Email já existente: email1@gmail.com", exception.getMessage());
    }

    @Test
    @DisplayName("Test altera usuario com email duplicado")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void updateEmailDuplicatedTest() {
        User user = new User(5, "User2", "email1@gmail.com", "123", "ADMIN");
<<<<<<< HEAD
        var exception = assertThrows(IntegrityViolation.class, () -> service.update(user));
=======
        var exception = assertThrows(IntegrityViolation.class, () -> userService.update(user));
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
        assertEquals("Email já existente: email1@gmail.com", exception.getMessage());

    }

    @Test
    @DisplayName("Teste buscar usuario por nome")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByNameTest() {
        var usuario = service.findByNameStartsWithIgnoreCase("User 1");
        assertNotNull(usuario);
        assertEquals(1, usuario.size());
        var usuario1 = service.findByNameStartsWithIgnoreCase("User 2");
        assertEquals(1, usuario1.size());
    }

    @Test
    @DisplayName("Teste buscar usuario por nome errado")
    @Sql({ "classpath:/resources/sqls/usuario.sql" })
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameStartsWithIgnoreCase("w"));
        assertEquals("Nenhum usário inicia com w", exception.getMessage());

    }

}