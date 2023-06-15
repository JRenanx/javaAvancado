package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Campeonato;

public interface CampeonatoService {

    Campeonato findById(Integer id);
    
    Campeonato insert(Campeonato campeonato);
    
    List<Campeonato> listAll();
    
    Campeonato update (Campeonato campeonato);
    
    void delete (Integer id);
}
