package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Pais;

public interface PaisService {

    Pais findById(Integer id);

    Pais insert(Pais pais);

    Pais update(Pais pais);

    void delete(Integer id);

    List<Pais> listAll();

    List<Pais> findByNameStartsWithIgnoreCase(String name);

}
