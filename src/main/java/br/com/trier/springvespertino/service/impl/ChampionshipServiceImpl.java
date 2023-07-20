package br.com.trier.springvespertino.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.repositories.ChampionshipRepository;
import br.com.trier.springvespertino.service.ChampionshipService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    @Autowired
    private ChampionshipRepository repository;

    private void validateYear(Integer year) {
        if (year == null) {
            throw new IntegrityViolation("Ano não pode ser nulo.");
        }
        if (year < 1990 || year > LocalDateTime.now().plusYears(1).getYear()) {
            throw new IntegrityViolation("Campeonato deve estar estre os anos de 1990 e %s"
                    .formatted(LocalDateTime.now().plusYears(1).getYear()));
        }
    }

    @Override
    public Championship findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Campeonato %s não existe.".formatted(id)));
    }

    @Override
    public Championship insert(Championship championship) {
        validateYear(championship.getYear());
        return repository.save(championship);
    }

    @Override
    public Championship update(Championship championship) {
        validateYear(championship.getYear());
        findById(championship.getId());
        return repository.save(championship);
    }

    @Override
    public void delete(Integer id) {
        Championship championship = findById(id);
        repository.delete(championship);
    }

    @Override
    public List<Championship> listAll() {
        List<Championship> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum campeonato cadastrado.");
        }
        return lista;
    }

    @Override
    public List<Championship> findByYear(Integer year) {
        List<Championship> lista = repository.findByYear(year);
        validateYear(year);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum campeonato em %s".formatted(year));
        }
        return repository.findByYear(year);
    }

    @Override
    public List<Championship> findByYearBetween(Integer start, Integer end) {
        return repository.findByYearBetween(start, end);
    }

    @Override
    public List<Championship> findByDescriptionContaining(String description) {
        return null;
    }

}