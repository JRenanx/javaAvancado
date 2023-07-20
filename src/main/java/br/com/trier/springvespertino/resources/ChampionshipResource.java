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

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.service.ChampionshipService;

@RestController
@RequestMapping("/championships")
public class ChampionshipResource {

    @Autowired
    private ChampionshipService service;

    @GetMapping("/{id}")
    public ResponseEntity<Championship> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Championship> insert(@RequestBody Championship championship) {
        return ResponseEntity.ok(service.insert(championship));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Championship> update(@PathVariable Integer id, @RequestBody Championship championship) {
        championship.setId(id);
        return ResponseEntity.ok(service.insert(championship));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Championship>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/list/{start}/{end}")
    public ResponseEntity<List<Championship>> findByYearBetween(@PathVariable Integer start, @PathVariable Integer end) {
        return ResponseEntity.ok(service.findByYearBetween(start, end));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Championship>> findByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(service.findByYear(year));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Championship>> findByDescriptionContaining(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescriptionContaining(description));
    }



}