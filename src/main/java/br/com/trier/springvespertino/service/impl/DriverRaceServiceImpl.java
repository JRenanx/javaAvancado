package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Driver;
import br.com.trier.springvespertino.models.DriverRace;
import br.com.trier.springvespertino.repositories.DriverRaceRepository;
import br.com.trier.springvespertino.service.DriverRaceService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class DriverRaceServiceImpl implements DriverRaceService {

    @Autowired
    private DriverRaceRepository repository;

    @Override
    public DriverRace findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Piloto/Corrida %s não existe.".formatted(id)));
    }

    @Override
    public DriverRace insert(DriverRace pilotoCorrida) {
        return repository.save(pilotoCorrida);
    }

    @Override
    public List<DriverRace> listAll() {
        List<DriverRace> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto/corrida encontrado.");
        }
        return null;
    }

    @Override
    public DriverRace update(DriverRace driverRace) {
        findById(driverRace.getId());
        return repository.save(driverRace);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));

    }

    @Override
    public List<DriverRace> findByRaceOrderByPosition(Race race) {
        List<DriverRace> list = repository.findByRaceOrderByPosition(race);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto encontrado para a corrida: %s".formatted(race.getId()));
        }
        return list;
    }

    @Override
    public List<DriverRace> findByDriverOrderByPosition(Driver driver) {
        List<DriverRace> list = repository.findByDriverOrderByPosition(driver);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nemhum piloto encontrado nesta posição.");
        }
        return list;
    }

    @Override
    public List<DriverRace> findByPosition(Integer position) {
        List<DriverRace> lista = repository.findByPosition(position);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado na posição: %s".formatted(position));
        }
        return lista;
    }

}