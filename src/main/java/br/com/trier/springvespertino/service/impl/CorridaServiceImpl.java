package br.com.trier.springvespertino.service.impl;



import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Campeonato;
import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Pista;
import br.com.trier.springvespertino.repositories.CorridaRepository;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class CorridaServiceImpl implements CorridaService{
	
	@Autowired
    private CorridaRepository repository;
	
	ZonedDateTime zonedDateTime = ZonedDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
	String formatedString = formatter.format(zonedDateTime);
	
	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound ("Corrida %s não encontrada.".formatted(id)));
	}

	@Override
	public Corrida insert(Corrida corrida) {
		return repository.save(corrida);
	}

	@Override
	public List<Corrida> listAll() {
		List<Corrida> lista = repository.findAll(); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada.");
        }
        return lista;
	}

	@Override
	public Corrida update(Corrida corrida) {
		findById(corrida.getId());
        return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
        repository.delete(corrida); 
	}

	@Override
	public List<Corrida> findByDate(ZonedDateTime date) {
		List<Corrida> lista = repository.findByDate(date);
	    if (lista.isEmpty()) {
	        throw new ObjectNotFound("Corrida não encontrada encontrada.");
	    }
	    return lista;
	}

	@Override
	public List<Corrida> findByDateBetween(ZonedDateTime date1, ZonedDateTime date2) {
		List<Corrida> lista = repository.findByDateBetween(date1, date2);
	    if (lista.isEmpty()) {
	        throw new ObjectNotFound("Nenhuma corrida foi encontrada entre a data selecionada");
	    }
	    return lista;
	}

	@Override
	public List<Corrida> findByPistaOrderByDate(Pista pista) {
		List<Corrida> lista = repository.findByPistaOrderByDate(pista); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada na pista: %s".formatted(pista.getName()));
        }
        return lista;
	}

	@Override
	public List<Corrida> findByCampeonatoOrderByDate(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonatoOrderByDate(campeonato); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada no campeonato: %s".formatted(campeonato.getDescription()));
        }
        return lista;
	}

}