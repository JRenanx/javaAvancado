package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer> {

    List<Piloto> findByNameContainingIgnoreCase(String name);

    List<Piloto> findByNameStartsWithIgnoreCase(String name);
}