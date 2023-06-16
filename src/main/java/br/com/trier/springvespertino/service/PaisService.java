package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Pais;

public interface PaisService {

    Pais findById(Integer id);
    
    List<Pais> findByName(String name);
    
    Pais insert (Pais pais);
    
    List<Pais> listAll();
    
    Pais update(Pais pais);
    
    void delete(Integer id);
    
    
}
