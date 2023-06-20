package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Equipe;
import br.com.trier.springvespertino.models.dto.EquipeDTO;
import br.com.trier.springvespertino.service.EquipeService;

@RestController
@RequestMapping("/equipe")
public class EquipeResource {

    @Autowired
    private EquipeService service;

    @PostMapping
    public ResponseEntity<EquipeDTO> insert(@RequestBody EquipeDTO equipe) {
        return ResponseEntity.ok(service.insert(new Equipe(equipe)).toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> findById(@PathVariable Integer id) {
        Equipe equipe = service.findById(id);
        return ResponseEntity.ok(equipe.toDTO());
    }

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDTO()).toList());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EquipeDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name).stream().map((equipe) -> equipe.toDTO()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeDTO> update(@PathVariable Integer id, @RequestBody EquipeDTO equipeDTO) {
        Equipe equipe = new Equipe(equipeDTO);
        equipe.setId(id);
        return ResponseEntity.ok(service.update(equipe).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
