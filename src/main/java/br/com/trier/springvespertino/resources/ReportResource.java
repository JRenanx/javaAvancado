package br.com.trier.springvespertino.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.dto.CorridaDTO;
import br.com.trier.springvespertino.models.dto.CorridaPaisAnoDTO;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;


@RestController
@RequestMapping("/reports")
public class ReportResource {

    @Autowired
    private CorridaService service;
    
    @Autowired
    private PaisService paisService;
    
    @Autowired
    private PistaService pistaService;
    
//    @GetMapping("/races-by-country-year/{countryId}/{year}")
//    public ResponseEntity<CorridaPaisAnoDTO> findRaceByCountryAndYear(@PathVariable Integer countryId, @PathVariable Integer year){
//        
//        Pais pais = paisService.findById(countryId);
//        
//        List<CorridaDTO> raceDTOs = pistaService.findByPaisOrderBySizeDesc(pais).stream()
//                .flatMap(speedway -> {
//                    try {
//                        return service.findByPistaOrderByDate(speedway).stream();
//                    } catch (ObjectNotFound e) {
//                        return Stream.empty();
//                    }
//                })
//                .filter(race -> race.getDate().getYear() == year)
//                .map(Corrida::toDTO).toList();
//        
//                
//        return ResponseEntity.ok(new CorridaDTO(year, pais.getName(), raceDTOs.size(), raceDTOs));
//        
//        
//    }
    
    
}
