package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.repositories.PilotoRepository;
import br.com.trier.springvespertino.service.PilotoService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class PilotoServiceImpl implements PilotoService {

    @Autowired
    private PilotoRepository repository;

    @Override
    public Piloto findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Piloto %s nao encontrado.".formatted(id)));
    }

    @Override
    public Piloto insert(Piloto pilot) {
        return repository.save(pilot);
    }

    @Override
    public List<Piloto> listAll() {
        List<Piloto> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado.");
        }
        return lista;
    }

    @Override
    public Piloto update(Piloto piloto) {
        findById(piloto.getId());
        return repository.save(piloto);
    }

    @Override
    public void delete(Integer id) {
        Piloto piloto = findById(id);
        repository.delete(piloto);
    }

    @Override
    public List<Piloto> findByNameContainingIgnoreCase(String name) {
        List<Piloto> lista = repository.findByNameContainingIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto com esse nome.");
        }
        return lista;
    }

    @Override
    public List<Piloto> findByNameStartsWithIgnoreCase(String name) {
        List<Piloto> lista = repository.findByNameStartsWithIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto com esse nome.");
        }
        return lista;
    }

}