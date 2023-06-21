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
import br.com.trier.springvespertino.models.dto.CampeonatoDTO;
import br.com.trier.springvespertino.service.CampeonatoService;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoResource {

    @Autowired
    private CampeonatoService service;

    @PostMapping
    public ResponseEntity<CampeonatoDTO> insert(@RequestBody CampeonatoDTO camp) {
        return ResponseEntity.ok(service.insert(new Campeonato(camp)).toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> findById(@PathVariable Integer id) {
        Campeonato camp = service.findById(id);
        return ResponseEntity.ok(camp.toDTO()) ;
    }

    @GetMapping("/lista/{start}/{end}")
    public ResponseEntity<List<CampeonatoDTO>> findByYearBetween(@PathVariable Integer start, @PathVariable Integer end) {
        return ResponseEntity.ok(service.findByYearBetween(start, end).stream().map((campeonato) -> campeonato.toDTO()).toList());
    }

    @GetMapping("/ano/{year}")
    public ResponseEntity<List<CampeonatoDTO>> findByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(service.findByYear(year).stream().map((campeonato) -> campeonato.toDTO()).toList());
    }

    @GetMapping("/ano/{start}/{end}/{description}")
    public ResponseEntity<List<CampeonatoDTO>> findByYearAndDescription(@PathVariable Integer start,@PathVariable Integer end, @PathVariable String description) {
        return ResponseEntity.ok(service.findByYearAndDescription(start, end, description).stream().map((campeonato) -> campeonato.toDTO()).toList());
    }

    @GetMapping
    public ResponseEntity<List<CampeonatoDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((campeonato) -> campeonato.toDTO()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> update(@PathVariable Integer id, @RequestBody Campeonato camp) {
        camp.setId(id);
        return ResponseEntity.ok(service.update(camp).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}