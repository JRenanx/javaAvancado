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

import br.com.trier.springvespertino.models.DriverRace;
import br.com.trier.springvespertino.models.dto.DriverRaceDTO;
import br.com.trier.springvespertino.service.RaceService;
import br.com.trier.springvespertino.service.DriverRaceService;
import br.com.trier.springvespertino.service.DriverService;

@RestController
@RequestMapping("/drivers/races")
public class DriverRaceResource {

    @Autowired
    private DriverRaceService service;

    @Autowired
    private DriverService pilotService;

    @Autowired
    private RaceService raceService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverRaceDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<DriverRaceDTO> insert(@RequestBody DriverRaceDTO driverRacerDTO) {
        DriverRace pilotR = new DriverRace(driverRacerDTO, pilotService.findById(driverRacerDTO.getDriverId()),
                raceService.findById(driverRacerDTO.getRaceId()));
        return ResponseEntity.ok(service.insert(pilotR).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<DriverRaceDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(pilotR -> pilotR.toDTO()).toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<DriverRaceDTO> update(@RequestBody DriverRaceDTO driverRacerDTO,
            @PathVariable Integer id) {
        DriverRace pilotR = new DriverRace(driverRacerDTO, pilotService.findById(driverRacerDTO.getDriverId()),
                raceService.findById(driverRacerDTO.getRaceId()));
        pilotR.setId(id);
        return ResponseEntity.ok(service.update(pilotR).toDTO());

    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<DriverRaceDTO>> findByPlacement(@PathVariable Integer position) {
        return ResponseEntity.ok(service.findByPosition(position).stream().map(pilot -> pilot.toDTO()).toList());
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<List<DriverRaceDTO>> findByDriverOrderByPosition(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findByDriverOrderByPosition(pilotService.findById(id)).stream()
                .map(pilot -> pilot.toDTO()).toList());
    }

    @GetMapping("/race/{id}")
    public ResponseEntity<List<DriverRaceDTO>> findByRaceOrderByPosition(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findByRaceOrderByPosition(raceService.findById(id)).stream()
                .map(pilot -> pilot.toDTO()).toList());
    }

}