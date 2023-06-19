package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Pais;

@Repository
public interface PaisRepository  extends JpaRepository<Pais, Integer>{
    
    List<Pais> findByNameStartingWithIgnoreCase(String name);


}
