package br.com.trier.springvespertino.service;



import java.util.List;

import br.com.trier.springvespertino.models.Equipe;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Piloto;

public interface PilotoService {
    
    Piloto findById(Integer id);

    Piloto insert(Piloto piloto);

    List<Piloto> listAll();

    Piloto update(Piloto piloto);

    void delete(Integer id);
    
    List<Piloto> findByNameStartsWithIgnoreCase (String name);
    
    List<Piloto> findByPaisOrderByName (Pais pais);
 
    List<Piloto> findByEquipeOrderByName (Equipe equipe);
}