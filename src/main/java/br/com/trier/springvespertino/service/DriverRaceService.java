package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.models.DriverRace;

public interface DriverRaceService {

    DriverRace findById(Integer id);

    DriverRace insert(DriverRace driverRace);

    DriverRace update(DriverRace driverRace);

    void delete(Integer id);

    List<DriverRace> listAll();

    List<DriverRace> findByDriverOrderByPosition(Driver driver);

    List<DriverRace> findByRaceOrderByPosition(Race race);

    List<DriverRace> findByPosition(Integer position);

}
