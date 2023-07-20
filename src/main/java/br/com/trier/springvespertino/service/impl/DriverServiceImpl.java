package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.repositories.DriverRepository;
import br.com.trier.springvespertino.service.DriverService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository repository;

    @Override
    public Driver findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Piloto %s nao encontrado.".formatted(id)));
    }

    @Override
    public Driver insert(Driver driver) {
        return repository.save(driver);
    }

    @Override
    public Driver update(Driver driver) {
        findById(driver.getId());
        return repository.save(driver);
    }

    @Override
    public void delete(Integer id) {
        Driver piloto = findById(id);
        repository.delete(piloto);
    }

    @Override
    public List<Driver> listAll() {
        List<Driver> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado.");
        }
        return lista;
    }

    @Override
    public List<Driver> findByNameStartsWithIgnoreCase(String name) {
        List<Driver> lista = repository.findByNameStartsWithIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto com esse nome.");
        }
        return lista;
    }

    @Override
    public List<Driver> findByCountryOrderByName(Country country) {
        List<Driver> lista = repository.findByCountryOrderByName(country);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado no país: %s".formatted(country.getName()));
        }
        return lista;
    }

    @Override
    public List<Driver> findByTeamOrderByName(Team team) {
        List<Driver> lista = repository.findByTeamOrderByName(team);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado na equipe: %s".formatted(team.getName()));
        }
        return lista;
    }

}