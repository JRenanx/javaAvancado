package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    List<Equipe> findByNameStartsWithIgnoreCase(String name);

}
