package br.com.trier.springvespertino.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.models.PilotoCorrida;

@Service
public interface PilotoCorridaService {
    
    PilotoCorrida findById(Integer id);
    
    PilotoCorrida insert(PilotoCorrida pilotRace);
    
    List<PilotoCorrida> listAll();
    
    PilotoCorrida update (PilotoCorrida pilotoCorrida);
    
    void delete(Integer id);
    
    List<PilotoCorrida> findByPilotOrderByPlacement(Piloto piloto);
    
    List<PilotoCorrida> findByRaceOrderByPlacement(Corrida corrida);
    
    List<PilotoCorrida> findByPlacement(Integer colocacao);
}