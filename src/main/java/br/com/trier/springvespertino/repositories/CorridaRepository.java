package br.com.trier.springvespertino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Corrida;

@Repository
public interface CorridaRepository {
    
    List<Corrida> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

}