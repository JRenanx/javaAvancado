package br.com.trier.springvespertino.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.repositories.TeamRepository;
import br.com.trier.springvespertino.service.TeamService;

@Repository
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository repository;

    @Override
    public Team findById(Integer id) {
        Optional<Team> equipe = repository.findById(id);
        return equipe.orElse(null);
    }

    @Override
    public Team insert(Team team) {
        return repository.save(team);
    }

    @Override
    public Team update(Team team) {
        return repository.save(team);
    }

    @Override
    public void delete(Integer id) {
        Team equipe = findById(id);
        if (equipe != null) {
            repository.delete(equipe);
        }
    }

    @Override
    public List<Team> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Team> findAllByOrderByName() {
        repository.findAll();
        return repository.findAllByOrderByName();
    }

    @Override
    public List<Team> findByNameStartsWithIgnoreCase(String name) {
        return repository.findByNameStartsWithIgnoreCase(name);
    }

}
