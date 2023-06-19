package br.com.trier.springvespertino.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Equipe;
import br.com.trier.springvespertino.repositories.EquipeRepository;
import br.com.trier.springvespertino.service.EquipeService;

@Repository
public class EquipeServiceImpl implements EquipeService {

    @Autowired
    private EquipeRepository repository;

    @Override
    public Equipe findById(Integer id) {
        Optional<Equipe> equipe = repository.findById(id);
        return equipe.orElse(null);
    }

    @Override
    public List<Equipe> findByName(String name) {
        return repository.findByNameStartingWithIgnoreCase(name);
    }
    
    @Override
    public Equipe insert(Equipe equipe) {
        return repository.save(equipe);
    }

    @Override
    public List<Equipe> listAll() {
        return repository.findAll();
    }

    @Override
    public Equipe update(Equipe equipe) {
        return repository.save(equipe);
    }

    @Override
    public void delete(Integer id) {
        Equipe equipe = findById(id);
        if (equipe != null) {
            repository.delete(equipe);
        }
    }



}
