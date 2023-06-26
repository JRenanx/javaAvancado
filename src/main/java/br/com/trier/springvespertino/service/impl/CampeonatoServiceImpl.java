package br.com.trier.springvespertino.service.impl;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.repositories.CampeonatoRepository;
import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {
    
    @Autowired
    private CampeonatoRepository repository;
       
    private void validateYear (Integer year) {
        if (year == null) {
            throw new IntegrityViolation("Ano não pode ser nulo.");
        }
        if (year < 1990 || year> LocalDateTime.now().plusYears(1).getYear()) {
            throw new IntegrityViolation("Campeonato deve estar estre os anos de 1990 e %s".formatted(LocalDateTime.now().plusYears(1).getYear()));
        }
    }
    
    @Override
    public Campeonato findById (Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Campeonato %s não existe.".formatted(id)));
    }
    
    @Override
    public List<Campeonato> findByYear(Integer year) {
        List<Campeonato> lista = repository.findByYear(year);
        validateYear(year);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum campeonato em %s".formatted(year));
        }
        return repository.findByYear(year);
    }

    @Override
    public Campeonato insert(Campeonato campeonato) {
        validateYear(campeonato.getYear());
        return repository.save(campeonato);
    }

    @Override
    public List<Campeonato> listAll() {
        List <Campeonato> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum campeonato cadastrado.");
        }
        return lista;
    }


    @Override
    public List<Campeonato> findByYearBetween(Integer start, Integer end) {
        return repository.findByYearBetween(start, end);
    }

    @Override
    public List<Campeonato> findByYearAndDescription(Integer start, Integer end, String description) {
        return repository.findByYearBetweenAndDescriptionContainingIgnoreCase(start, end, description);
    }
    
    @Override
    public Campeonato update(Campeonato campeonato) {
        validateYear(campeonato.getYear());
        findById(campeonato.getId());
        return repository.save(campeonato);
    }


    @Override
    public void delete(Integer id) {
        Campeonato campeonato = findById(id);
        repository.delete(campeonato);
    }
    
    

}