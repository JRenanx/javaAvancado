package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Pista;

public interface PistaService {

    Pista findById(Integer id);

    Pista insert(Pista user);

    List<Pista> listAll();

    Pista update(Pista user);

    void delete(Integer id);
    
    List<Pista> findByNameStartsWithIgnoreCase(String name);
    
    List<Pista> findBySizeBetween(Integer sizeIn, Integer sizeFinal);
    
    List<Pista> findByPaisOrderBySizeDesc(Pais pais);


}
