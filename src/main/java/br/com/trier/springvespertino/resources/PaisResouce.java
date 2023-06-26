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

import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.dto.PaisDTO;
import br.com.trier.springvespertino.service.PaisService;

@RestController
@RequestMapping("/paises")
public class PaisResouce {

    @Autowired
    private PaisService service;

    @PostMapping
    public ResponseEntity<PaisDTO> insert(@RequestBody PaisDTO pais) {
        return ResponseEntity.ok(service.insert(new Pais(pais)).toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> findById(@PathVariable Integer id) {
        Pais pais = service.findById(id);
        return ResponseEntity.ok(pais.toDTO());
    }

    @GetMapping
    public ResponseEntity<List<PaisDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((pais) -> pais.toDTO()).toList());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PaisDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name).stream().map((pais) -> pais.toDTO()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> update(@PathVariable Integer id, @RequestBody PaisDTO paisDTO) {
        Pais pais = new Pais(paisDTO);
        pais.setId(id);
        return ResponseEntity.ok(service.update(pais).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();

    }
}
