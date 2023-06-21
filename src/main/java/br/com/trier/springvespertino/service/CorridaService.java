package br.com.trier.springvespertino.service;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.springvespertino.models.Corrida;

public interface CorridaService
{

    Corrida findById(Integer id);
    
    Corrida insert(Corrida corrida);
    
    List<Corrida> listAll();
    
    Corrida update(Corrida corrida);
    
    void delete(Integer id);
    
    List<Corrida> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);  
}