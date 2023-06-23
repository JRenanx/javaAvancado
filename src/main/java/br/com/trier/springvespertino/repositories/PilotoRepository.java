package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Equipe;
import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.models.PilotoCorrida;

public interface PilotoRepository extends JpaRepository <Piloto, Integer>{
    
    List<Piloto> findByNameStartsWithIgnoreCase (String name);
        
    List<Piloto> findByEquipeOrderByName (Equipe equipe);

    List<Piloto> findByPaisOrderByName(Pais pais);

}