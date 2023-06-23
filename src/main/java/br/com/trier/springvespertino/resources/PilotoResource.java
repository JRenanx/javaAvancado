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

import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.service.EquipeService;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PilotoService;

@RestController
@RequestMapping("/pilotos")
public class PilotoResource {
    
    @Autowired
    private PilotoService service;
    
    @Autowired
    private PaisService paisService;
    
    @Autowired 
    private EquipeService equipeService;
    
    @GetMapping ("/{id}")
    public ResponseEntity <Piloto> findById (@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    ResponseEntity <Piloto> insert (@RequestBody Piloto piloto) {
        paisService.findById(piloto.getPais().getId());
        equipeService.findById(piloto.getPais().getId());
        return ResponseEntity.ok(service.insert(piloto));
    }
    
    @GetMapping
    ResponseEntity <List<Piloto>> listAll () {
        return ResponseEntity.ok(service.listAll());
    }
    
    @PutMapping("/{id}")
    ResponseEntity <Piloto> update (@PathVariable Integer id, @RequestBody Piloto piloto) {
        paisService.findById(piloto.getPais().getId());
        equipeService.findById(piloto.getPais().getId());
        piloto.setId(id);
        return ResponseEntity.ok(service.update(piloto));
    }
    
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<Piloto>> findByNameStartsWithIgnoreCase(@PathVariable String name){
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
    }
    
    @GetMapping("/pais/{id}")
    public ResponseEntity<List<Piloto>> findByPaisOrderByName(@PathVariable Integer id){
        return ResponseEntity.ok(service.findByPaisOrderByName(paisService.findById(id)));
    }
    
    @GetMapping("/equipe/{id}")
    public ResponseEntity<List<Piloto>> findByEquipeOrderByName (@PathVariable Integer id){
        return ResponseEntity.ok(service.findByEquipeOrderByName(equipeService.findById(id)));
    }
}