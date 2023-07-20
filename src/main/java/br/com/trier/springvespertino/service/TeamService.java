package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Team;

public interface TeamService {

    Team findById(Integer id);

    Team insert(Team team);

    Team update(Team team);

    void delete(Integer id);

    List<Team> listAll();
    
    List<Team> findAllByOrderByName();

    List<Team> findByNameStartsWithIgnoreCase(String name);

}
