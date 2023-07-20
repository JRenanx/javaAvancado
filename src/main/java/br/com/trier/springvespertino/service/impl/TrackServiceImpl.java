package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Track;
import br.com.trier.springvespertino.repositories.TrackRepository;
import br.com.trier.springvespertino.service.TrackService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackRepository repository;

    private void validatePista(Track track) {
        if (track.getSize() == null || track.getSize() <= 0) {
            throw new IntegrityViolation("Tamanho da pista inválido.");
        }
    }

    @Override
    public Track findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Pista %s não existe.".formatted(id)));
    }

    @Override
    public Track insert(Track track) {
        validatePista(track);
        return repository.save(track);
    }

    @Override
    public Track update(Track track) {
        findById(track.getId());
        validatePista(track);
        return repository.save(track);
    }

    @Override
    public void delete(Integer id) {
        Track pista = findById(id);
        repository.delete(pista);
    }

    @Override
    public List<Track> findByCountryOrderBySizeDesc(Country track) {
        List<Track> lista = repository.findByCountryOrderBySizeDesc(track);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada no país: %s".formatted(track.getName()));
        }
        return lista;
    }

    @Override
    public List<Track> findByNameStartsWithIgnoreCase(String name) {
        List<Track> lista = repository.findByNameStartsWithIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada com esse nome.");
        }
        return lista;
    }

    @Override
    public List<Track> listAll() {
        List<Track> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada.");
        }
        return lista;
    }

    @Override
    public List<Track> findBySizeBetween(Integer sizeIn, Integer sizeFinal) {
        List<Track> lista = repository.findBySizeBetween(sizeIn, sizeFinal);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada com essas medidas.");
        }
        return lista;
    }

}
