package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Driver;

public interface DriverService {

    Driver findById(Integer id);

    Driver insert(Driver driver);

    Driver update(Driver driver);

    void delete(Integer id);

    List<Driver> listAll();

    List<Driver> findByNameStartsWithIgnoreCase(String name);

    List<Driver> findByCountryOrderByName(Country country);

    List<Driver> findByTeamOrderByName(Team team);
}