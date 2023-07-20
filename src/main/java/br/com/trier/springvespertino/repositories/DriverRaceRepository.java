package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.models.DriverRace;

@Repository
public interface DriverRaceRepository extends JpaRepository<DriverRace, Integer>{

    List<DriverRace> findByDriverOrderByPosition(Driver driver);
    
    List<DriverRace> findByRaceOrderByPosition(Race race);
    
    List<DriverRace> findByPosition(Integer position);
}