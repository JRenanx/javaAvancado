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

import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.service.TeamService;
import br.com.trier.springvespertino.service.CountryService;
import br.com.trier.springvespertino.service.DriverService;

@RestController
@RequestMapping("/drivers")
public class DriverResource {
    
    @Autowired
    private DriverService service;
    
    @Autowired
    private CountryService paisService;
    
    @Autowired 
    private TeamService equipeService;
    
    @GetMapping ("/{id}")
    public ResponseEntity <Driver> findById (@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    public ResponseEntity <Driver> insert (@RequestBody Driver driver) {
        paisService.findById(driver.getCountry().getId());
        equipeService.findById(driver.getCountry().getId());
        return ResponseEntity.ok(service.insert(driver));
    }
    
    @GetMapping
    public ResponseEntity <List<Driver>> listAll () {
        return ResponseEntity.ok(service.listAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity <Driver> update (@PathVariable Integer id, @RequestBody Driver driver) {
        paisService.findById(driver.getCountry().getId());
        equipeService.findById(driver.getCountry().getId());
        driver.setId(id);
        return ResponseEntity.ok(service.update(driver));
    }
    
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Driver>> findByNameStartsWithIgnoreCase(@PathVariable String name){
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
    }
    
    @GetMapping("/country/{id}")
    public ResponseEntity<List<Driver>> findByCountryOrderByName(@PathVariable Integer id){
        return ResponseEntity.ok(service.findByCountryOrderByName(paisService.findById(id)));
    }
    
    @GetMapping("/team/{id}")
    public ResponseEntity<List<Driver>> findByTeamOrderByName (@PathVariable Integer id){
        return ResponseEntity.ok(service.findByTeamOrderByName(equipeService.findById(id)));
    }
}