package br.com.trier.springvespertino.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.models.dto.CorridaPaisAnoDTO;
import br.com.trier.springvespertino.service.RaceService;
import br.com.trier.springvespertino.service.CountryService;
import br.com.trier.springvespertino.service.TrackService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;


@RestController
@RequestMapping("/reports")
public class ReportResource {

    @Autowired
    private RaceService service;
    
    @Autowired
    private CountryService paisService;
    
    @Autowired
    private TrackService pistaService;
    
//    @GetMapping("/races-by-country-year/{countryId}/{year}")
//    public ResponseEntity<CorridaPaisAnoDTO> findRaceByCountryAndYear(@PathVariable Integer countryId, @PathVariable Integer year){
//        
//        Pais pais = paisService.findById(countryId);
//        
//        List<CorridaDTO> raceDTOs = pistaService.findByPaisOrderBySizeDesc(pais).stream()
//                .flatMap(corrida -> {
//                    try {
//                        return service.findByPistaOrderByDate(corrida).stream();
//                    } catch (ObjectNotFound e) {
//                        return Stream.empty();
//                    }
//                })
//                .filter(corrida -> corrida.getDate().getYear() == year)
//                .map(corrida::toDTO).toList();
//        
//                
//        return ResponseEntity.ok(new CorridaDTO(year, pais.getName(), raceDTOs.size(), raceDTOs));
//        
//        
//    }
    
    
}
