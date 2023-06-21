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

import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PistaService;

@RestController
@RequestMapping("/pistas")
public class PistaResource {

    @Autowired
    private PistaService service;

    @Autowired
    private PaisService PaisService;

    @GetMapping("/{id}")
    public ResponseEntity<Pista> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    ResponseEntity<Pista> insert(@RequestBody Pista pista) {
        PaisService.findById(pista.getPais().getId());
        return ResponseEntity.ok(service.insert(pista));
    }

    @GetMapping
    ResponseEntity<List<Pista>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    ResponseEntity<Pista> update(@RequestBody Integer id, @RequestBody Pista pista) {
        PaisService.findById(pista.getPais().getId());
        pista.setId(id);
        return ResponseEntity.ok(service.update(pista));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    ResponseEntity<List<Pista>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
    }

    @GetMapping("/size/{sizeIn}")
    ResponseEntity<List<Pista>> findBySizeBetween(@PathVariable Integer sizeIn, @PathVariable Integer sizeFinal) {
        return ResponseEntity.ok(service.findBySizeBetween(sizeIn, sizeFinal));
    }

    @GetMapping("/pais/{idPais}")
    ResponseEntity<List<Pista>> findByPaisOrderBySizeDesc(@PathVariable Integer idPais) {
        return ResponseEntity.ok(service.findByPaisOrderBySizeDesc(PaisService.findById(idPais)));
    }
}
