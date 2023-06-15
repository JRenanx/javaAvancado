package br.com.trier.springvespertino.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.repositories.PaisRepository;
import br.com.trier.springvespertino.service.PaisService;

@Service
public class PaisServiceImpl implements PaisService{

    @Autowired
    private PaisRepository repository;

    @Override
    public Pais findById(Integer id) {
        Optional<Pais> user = repository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Pais insert(Pais user) {
        return repository.save(user);
    }

    @Override
    public List<Pais> listAll() {
        return repository.findAll();
    }

    @Override
    public Pais update(Pais user) {
        return repository.save(user);
    }

    @Override
    public void delete(Integer id) {
        Pais pais = findById(id);
        if (pais !=null) {
            repository.delete(pais);
        }
    }

}

