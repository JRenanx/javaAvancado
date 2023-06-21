package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer>{
    
    List<Pista> findByNameStartsWithIgnoreCase(String name);
    
    List<Pista> findBySizeBetween(Integer sizeIn, Integer sizeFinal);
    
    List<Pista> findByPaisOrderBySizeDesc(Pais pais);

}
