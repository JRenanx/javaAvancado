package br.com.trier.springvespertino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Pista;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer> {
    
    List<Corrida> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

    List<Corrida> findByDate(ZonedDateTime date);

    List<Corrida> findByPistaOrderByDate(Pista pista);

    List<Corrida> findByCampeonatoOrderByDate(Campeonato campeonato);

}