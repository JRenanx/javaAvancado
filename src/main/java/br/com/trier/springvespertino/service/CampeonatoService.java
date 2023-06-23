package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Campeonato;

public interface CampeonatoService {

    Campeonato findById(Integer id);

    Campeonato insert(Campeonato campeonato);

    Campeonato update(Campeonato campeonato);

    void delete(Integer id);
    
    List<Campeonato> listAll();

    List<Campeonato> findByYear(Integer year);

    List<Campeonato> findByYearBetween(Integer start, Integer end);

    List<Campeonato> findByYearAndDescription(Integer start, Integer end, String description);

}