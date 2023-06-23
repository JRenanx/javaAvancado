package br.com.trier.springvespertino.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.dto.PilotoCorridaDTO;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.PilotoCorridaService;
import br.com.trier.springvespertino.service.PilotoService;

@RestController
@RequestMapping("/pilotos/corridas")
public class PilotoCorridaResource {

    @Autowired
    private PilotoCorridaService service;

    @Autowired
    private PilotoService pilotoService;
    @Autowired
    private CorridaService corridaService;

    @GetMapping("/{id}")
    public ResponseEntity<PilotoCorridaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }
}
