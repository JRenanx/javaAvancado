package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Equipe;

public interface EquipeService {

    Equipe findById(Integer id);

    Equipe insert(Equipe equipe);

    List<Equipe> listAll();

    Equipe update(Equipe equipe);

    void delete(Integer id);

}
