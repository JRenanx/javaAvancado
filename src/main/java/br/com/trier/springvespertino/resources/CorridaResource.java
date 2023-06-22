package br.com.trier.springvespertino.resources;

import java.time.ZonedDateTime;
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

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.service.CorridaService;

@RestController
@RequestMapping("/corridas")
public class CorridaResource {

    @Autowired
    private CorridaService service;

    @PostMapping
    public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida) {
        Corrida newCorrida = service.insert(corrida);
        return ResponseEntity.ok(newCorrida);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corrida> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Corrida>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida corrida) {
        corrida.setId(id);
        corrida = service.update(corrida);
        return ResponseEntity.ok(corrida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dateIn}/{dateFin}")
    public ResponseEntity<List<Corrida>> findByDateBetween(@PathVariable ZonedDateTime dateIn, @PathVariable ZonedDateTime dateFin) {
        return ResponseEntity.ok(service.findByDateBetween(dateIn, dateFin));
    }
}