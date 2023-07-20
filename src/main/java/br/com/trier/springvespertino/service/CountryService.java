package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Country;

public interface CountryService {

    Country findById(Integer id);

    Country insert(Country country);

    Country update(Country country);

    void delete(Integer id);

    List<Country> listAll();

    List<Country> findByNameStartsWithIgnoreCase(String name);
    
    List<Country> findAllByOrderByName();

}
