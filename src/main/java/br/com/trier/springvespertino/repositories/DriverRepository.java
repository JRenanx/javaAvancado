package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.models.Team;

@Repository
public interface DriverRepository extends JpaRepository <Driver, Integer>{
    
    List<Driver> findByNameStartsWithIgnoreCase (String name);
        
    List<Driver> findByTeamOrderByName (Team team);

    List<Driver> findByCountryOrderByName(Country country);

}