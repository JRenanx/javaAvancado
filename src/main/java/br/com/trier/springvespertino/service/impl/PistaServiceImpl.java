package br.com.trier.springvespertino.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Pais;
import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.repositories.PistaRepository;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class PistaServiceImpl implements PistaService {

    @Autowired
    private PistaRepository repository;

    private void validatePista(Pista pista) {
        if (pista.getSize() == null || pista.getSize() <= 0) {
            throw new IntegrityViolation("Tamanho da pista invalido");
        }
    }

    @Override
    public Pista findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Pista %s não existe.".formatted(id)));
    }

    @Override
    public Pista insert(Pista pista) {
        validatePista(pista);
        return repository.save(pista);
    }

    @Override
    public List<Pista> listAll() {
        List<Pista> lista = repository.findAll();
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada.");
        }
        return lista;
    }

    @Override
    public Pista update(Pista pista) {
        findById(pista.getId());
        validatePista(pista);
        return repository.save(pista);
    }

    @Override
    public void delete(Integer id) {
        Pista pista = findById(id);
        repository.delete(pista);
    }

    @Override
    public List<Pista> findByNameStartsWithIgnoreCase(String name) {
        List<Pista> lista = repository.findByNameStartsWithIgnoreCase(name);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada com esse nome.");
        }
        return lista;
    }

    @Override
    public List<Pista> findBySizeBetween(Integer sizeIn, Integer sizeFinal) {
        List<Pista> lista = repository.findBySizeBetween(sizeIn, sizeFinal);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada com essas medidas.");
        }
        return lista;
    }

    @Override
    public List<Pista> findByPaisOrderBySizeDesc(Pais pais) {
        List<Pista> lista = repository.findByPaisOrderBySizeDesc(pais);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma pista cadastrada neste pais: %s".formatted(pais.getName()));
        }
        return lista;
    }
    
    
    
    

}
