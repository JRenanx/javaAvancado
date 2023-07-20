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

import br.com.trier.springvespertino.models.Track;
import br.com.trier.springvespertino.service.CountryService;
import br.com.trier.springvespertino.service.TrackService;

@RestController
@RequestMapping("/tracks")
public class TrackResource {

    @Autowired
    private TrackService service;

    @Autowired
    private CountryService countryService;

//  @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Track> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

//  @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Track> insert(@RequestBody Track track){
        countryService.findById(track.getCountry().getId());
        return ResponseEntity.ok(service.insert(track));
    }


//  @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<Track>> listAll(){
        return ResponseEntity.ok(service.listAll());
    }

//  @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Track> update (@RequestBody Track track, @PathVariable Integer id){
        countryService.findById(track.getId());
        track.setId(id);
        return ResponseEntity.ok(service.update(track));
        
    }

//  @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

 // @Secured({"ROLE_USER"})
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Track>> findByNameStartsWithIgnoreCase(@PathVariable String name){
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
        
    }

 // @Secured({"ROLE_USER"})
    @GetMapping("/size/{sizeIn}/{sizeFin}")
    public ResponseEntity<List<Track>> findBySizeBetween(@PathVariable Integer sizeIn, @PathVariable Integer sizeFin){
        return ResponseEntity.ok(service.findBySizeBetween(sizeIn, sizeFin));
    }

 // @Secured({"ROLE_USER"})
    @GetMapping("/country/{idPais}")
    public ResponseEntity<List<Track>> findByCountryOrderBySizeDesc(@PathVariable Integer idCountry){
        return ResponseEntity.ok(service.findByCountryOrderBySizeDesc(countryService.findById(idCountry)));
    }
}
