package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Campeonato;

@Repository
public interface CampeonatoRepository  extends JpaRepository<Campeonato, Integer>{

    List<Campeonato> findByYear(Integer year);
    
    List<Campeonato> findByYearBetween(Integer start, Integer end);
    
    List<Campeonato> findByYearBetweenAndDescriptionContainingIgnoreCase(Integer firstYear, Integer lastYear, String description);



}