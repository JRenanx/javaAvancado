package br.com.trier.springvespertino.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.repositories.CorridaRepository;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class CorridaServiceImpl implements CorridaService {

    @Autowired
    private CorridaRepository repository;

    private void validateDate(Corrida corrida) {
        if (corrida.getDate().getYear() == corrida.getCampeonato().getYear()) {
            throw new ObjectNotFound("data da corrida precisa ser no mesmo ano que do campeonato");
        }
    }

    @Override
    public Corrida findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("corrida %s não encontrada"));
    }

    @Override
    public Corrida insert(Corrida corrida) {
        validateDate(corrida);
        return repository.save(corrida);
    }

    @Override
    public List<Corrida> listAll() {
        List<Corrida> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("nenhuma corrida cadastrada");
        }
        return lista;
    }

    @Override
    public Corrida update(Corrida corrida) {
        findById(corrida.getId());
        validateDate(corrida);
        return repository.save(corrida);
    }

    @Override
    public void delete(Integer id) {
        Corrida corrida = findById(id);
        repository.delete(corrida);
    }

    @Override
    public List<Corrida> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin) {
        List<Corrida> lista = repository.findByDateBetween(dateIn, dateFin);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("nenhuma corrida cadastrada");
        }
        return lista;
    }
