package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.PilotoCorrida;
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
    private PilotoService pilotService;

    @Autowired
    private CorridaService raceService;

    @GetMapping("/{id}")
    public ResponseEntity<PilotoCorridaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<PilotoCorridaDTO> insert(@RequestBody PilotoCorridaDTO pilotRaceDTO) {
        PilotoCorrida pilotR = new PilotoCorrida(pilotRaceDTO, pilotService.findById(pilotRaceDTO.getPilotoId()),
                raceService.findById(pilotRaceDTO.getCorridaId()));
        return ResponseEntity.ok(service.insert(pilotR).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<PilotoCorridaDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(pilotR -> pilotR.toDTO()).toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<PilotoCorridaDTO> update(@RequestBody PilotoCorridaDTO pilotRaceDTO,
            @PathVariable Integer id) {
        PilotoCorrida pilotR = new PilotoCorrida(pilotRaceDTO, pilotService.findById(pilotRaceDTO.getPilotoId()),
                raceService.findById(pilotRaceDTO.getCorridaId()));
        pilotR.setId(id);
        return ResponseEntity.ok(service.update(pilotR).toDTO());

    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByPlacement(@PathVariable Integer position) {
        return ResponseEntity.ok(service.findByPosition(position).stream().map(pilot -> pilot.toDTO()).toList());
    }

    @GetMapping("/piloto/{id}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByPilotoOrderByPosition(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findByPilotoOrderByPosition(pilotService.findById(id)).stream()
                .map(pilot -> pilot.toDTO()).toList());
    }

    @GetMapping("/corrida/{id}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByCorridaOrderByPosition(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findByCorridaOrderByPosition(raceService.findById(id)).stream()
                .map(pilot -> pilot.toDTO()).toList());
    }

}