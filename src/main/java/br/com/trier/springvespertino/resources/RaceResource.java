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

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.service.RaceService;

@RestController
@RequestMapping("/races")
public class RaceResource {

    @Autowired
    private RaceService service;

    @PostMapping
    public ResponseEntity<Race> insert(@RequestBody Race race) {
        Race newCorrida = service.insert(race);
        return ResponseEntity.ok(newCorrida);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Race> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Race>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Race> update(@PathVariable Integer id, @RequestBody Race race) {
        race.setId(id);
        race = service.update(race);
        return ResponseEntity.ok(race);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dateIn}/{dateFin}")
    public ResponseEntity<List<Race>> findByDateBetween(@PathVariable ZonedDateTime dateIn, @PathVariable ZonedDateTime dateFin) {
        return ResponseEntity.ok(service.findByDateBetween(dateIn, dateFin));
    }
}