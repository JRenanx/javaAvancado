package br.com.trier.springvespertino.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.repositories.CorridaRepository;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class CorridaServiceImpl implements CorridaService {

    @Autowired
    private CorridaRepository repository;

    private void validateRace(Corrida corrida) {
        if (corrida.getCampeonato() == null) {
            throw new IntegrityViolation("Campeonato nao pode ser nulo.");
        }
        if (corrida.getDate() == null) {
            throw new IntegrityViolation("Data invalida.");
        }
        int campeonatoAno = corrida.getCampeonato().getYear();
        int corridaAno = corrida.getDate().getYear();
        if (campeonatoAno != corridaAno) {
            throw new IntegrityViolation("Ano da corrida diferente do ano do campeonato.");
        }
    }

    @Override
    public Corrida findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Corrida %s não encontrada.".formatted(id)));
    }

    @Override
    public Corrida insert(Corrida corrida) {
        validateRace(corrida);
        return repository.save(corrida);
    }

    @Override
    public List<Corrida> listAll() {
        List<Corrida> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada.");
        }
        return lista;
    }

    @Override
    public Corrida update(Corrida corrida) {
        findById(corrida.getId());
        validateRace(corrida);
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
            throw new ObjectNotFound("Nenhuma corrida cadastrada.");
        }
        return lista;
    }
}
