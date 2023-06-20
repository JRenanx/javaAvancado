package br.com.trier.springvespertino.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.repositories.CampeonatoRepository;
import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;

@Repository
public class CampeonatoServiceImpl implements CampeonatoService {

    @Autowired
    CampeonatoRepository repository;

    private void validYear(Campeonato camp) {
        if (camp.getYear() == null) {
            throw new IntegrityViolation("Ano não pode ser null");
        }
        if (camp.getYear() < 1990 || camp.getYear() > LocalDateTime.now().getYear() + 1) {
            throw new IntegrityViolation("Ano inválido: %s".formatted(camp.getYear()));
        }
    }

    @Override
    public Campeonato findById(Integer id) {
        Optional<Campeonato> camp = repository.findById(id);
        return camp.orElse(null);
    }

    @Override
    public Campeonato insert(Campeonato camp) {
        validYear(camp);
        return repository.save(camp);
    }

    @Override
    public List<Campeonato> findByYear(Integer year) {
        return repository.findByYear(year);
    }

    @Override
    public List<Campeonato> findByYearBetween(Integer start, Integer end) {
        return repository.findByYearBetween(start, end);
    }

    @Override
    public List<Campeonato> findByYearAndDescription(Integer start, Integer end, String description) {
        return repository.findByYearBetweenAndDescriptionContainingIgnoreCase(start, end, description);
    }

    @Override
    public List<Campeonato> listAll() {
        return repository.findAll();
    }

    @Override
    public Campeonato update(Campeonato camp) {
        validYear(camp);
        return repository.save(camp);
    }

    @Override
    public void delete(Integer id) {
        Campeonato camp = findById(id);
        if (camp != null) {
            repository.delete(camp);
        }
    }

}