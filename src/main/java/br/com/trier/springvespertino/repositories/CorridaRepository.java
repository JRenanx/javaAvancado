package br.com.trier.springvespertino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Corrida;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer> {
    
    List<Corrida> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

}