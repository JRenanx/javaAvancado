package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.models.dto.UserDTO;
import br.com.trier.springvespertino.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService service;

<<<<<<< HEAD
     @Secured({"ROLE_ADMIN"})
=======
//    @Secured({ "ROLE_ADMIN" })
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
        return ResponseEntity.ok(service.insert(new User(user)).toDTO());
    }

<<<<<<< HEAD
     @Secured({"ROLE_USER"})
=======
//    @Secured({ "ROLE_USER" })
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

<<<<<<< HEAD
     @Secured({"ROLE_USER"})
=======
//    @Secured({ "ROLE_USER" })
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(user -> user.toDTO()).toList());
    }

<<<<<<< HEAD
     @Secured({"ROLE_USER"})
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        return ResponseEntity
                .ok(service.findByNameStartsWithIgnoreCase(name).stream().map(user -> user.toDTO()).toList());
    }

     @Secured({"ROLE_ADMIN"})
=======
//    @Secured({ "ROLE_USER" })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        return ResponseEntity
                .ok(service.findByNameStartsWithIgnoreCase(name).stream().map((user) -> user.toDTO()).toList());
    }

//    @Secured({ "ROLE_ADMIN" })
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        user.setId(id);
        return ResponseEntity.ok(service.update(user).toDTO());
    }

<<<<<<< HEAD
     @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
=======
//    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        findById(id);
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}