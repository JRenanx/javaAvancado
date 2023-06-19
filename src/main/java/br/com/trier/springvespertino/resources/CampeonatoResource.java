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

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.service.CampeonatoService;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoResource {

    @Autowired
    private CampeonatoService service;

    @PostMapping
    public ResponseEntity<Campeonato> insert(@RequestBody Campeonato camp) {
        Campeonato newCamp = service.insert(camp);
        return newCamp != null ? ResponseEntity.ok(newCamp) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> findById(@PathVariable Integer id) {
        Campeonato camp = service.findById(id);
        return camp != null ? ResponseEntity.ok(camp) : ResponseEntity.noContent().build();
    }

    @GetMapping("/lista/{start}/{end}")
    public ResponseEntity<List<Campeonato>> findByYearBetween(@PathVariable Integer start, @PathVariable Integer end) {
        List<Campeonato> camp = service.findByYearBetween(start, end);
        return camp != null ? ResponseEntity.ok(camp) : ResponseEntity.noContent().build();
    }

    @GetMapping("/ano/{year}")
    public ResponseEntity<List<Campeonato>> findByYear(@PathVariable Integer year) {
        List<Campeonato> list = service.findByYear(year);
        return list.size() > 0 ? ResponseEntity.ok(list) : ResponseEntity.noContent().build();
    }

    @GetMapping("/ano/{start}/{end}/{description}")
    public ResponseEntity<List<Campeonato>> findByYearAndDescription(@PathVariable Integer start,
            @PathVariable Integer end, @PathVariable String description) {
        List<Campeonato> list = service.findByYearAndDescription(start, end, description);
        return list.size() > 0 ? ResponseEntity.ok(list) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Campeonato>> listAll() {
        List<Campeonato> lista = service.listAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody Campeonato camp) {
        camp.setId(id);
        camp = service.update(camp);
        return camp != null ? ResponseEntity.ok(camp) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}