package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.models.PilotoCorrida;
import br.com.trier.springvespertino.repositories.PilotoCorridaRepository;
import br.com.trier.springvespertino.service.PilotoCorridaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class PilotoCorridaImpl implements PilotoCorridaService {

    @Autowired
    private PilotoCorridaRepository repository;

    @Override
    public PilotoCorrida findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Piloto/Corrida %s não existe.".formatted(id)));
    }

    @Override
    public PilotoCorrida insert(PilotoCorrida pilotoCorrida) {
        return repository.save(pilotoCorrida);
    }

    @Override
    public List<PilotoCorrida> listAll() {
        List<PilotoCorrida> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto/corrida encontrado.");
        }
        return null;
    }

    @Override
    public PilotoCorrida update(PilotoCorrida pilotoCorrida) {
        findById(pilotoCorrida.getId());
        return repository.save(pilotoCorrida);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));

    }

    @Override
    public List<PilotoCorrida> findByCorridaOrderByPosition(Corrida corrida) {
        List<PilotoCorrida> list = repository.findByCorridaOrderByPosition(corrida);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto encontrado para a corrida: %s".formatted(corrida.getId()));
        }
        return list;
    }

    @Override
    public List<PilotoCorrida> findByPilotoOrderByPosition(Piloto piloto) {
        List<PilotoCorrida> list = repository.findByPilotoOrderByPosition(piloto);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nemhum piloto encontrado nesta posição.");
        }
        return list;
    }

    @Override
    public List<PilotoCorrida> findByPosition(Integer position) {
        List<PilotoCorrida> lista = repository.findByPosition(position);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum piloto cadastrado na posição: %s".formatted(position));
        }
        return lista;
    }

}