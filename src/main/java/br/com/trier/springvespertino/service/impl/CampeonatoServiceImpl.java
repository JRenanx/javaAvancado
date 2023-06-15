package br.com.trier.springvespertino.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.repositories.CampeonatoRepository;
import br.com.trier.springvespertino.service.CampeonatoService;

@Repository
public class CampeonatoServiceImpl implements CampeonatoService{

   @Autowired
   CampeonatoRepository repository;
    
    @Override
    public Campeonato findById(Integer id) {
        Optional<Campeonato> camp = repository.findById(id);
        return camp.orElse(null);
    }

    @Override
    public Campeonato insert(Campeonato camp) {
        return repository.save(camp);
    }

    @Override
    public List<Campeonato> listAll() {
        return repository.findAll();
    }

    @Override
    public Campeonato update(Campeonato camp) {
        return repository.save(camp);
    }

    @Override
    public void delete(Integer id) {
        Campeonato camp = findById(id);
        if (camp !=null) {
            repository.delete(camp);
        }
    }

}
