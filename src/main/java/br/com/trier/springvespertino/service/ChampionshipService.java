package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Championship;

public interface ChampionshipService {

    Championship findById(Integer id);

    Championship insert(Championship championship);

    Championship update(Championship championship);

    void delete(Integer id);
    
    List<Championship> listAll();

    List<Championship> findByYear(Integer year);

    List<Championship> findByYearBetween(Integer start, Integer end);

    List<Championship> findByDescriptionContaining(String description);

}