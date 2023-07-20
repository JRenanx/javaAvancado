package br.com.trier.springvespertino.service.impl;



import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Track;
import br.com.trier.springvespertino.repositories.RaceRepository;
import br.com.trier.springvespertino.service.RaceService;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class RaceServiceImpl implements RaceService{
	
	@Autowired
    private RaceRepository repository;
	
	ZonedDateTime zonedDateTime = ZonedDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
	String formatedString = formatter.format(zonedDateTime);
	
	@Override
	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound ("Corrida %s não encontrada.".formatted(id)));
	}

	@Override
	public Race insert(Race race) {
		return repository.save(race);
	}

	@Override
	public List<Race> listAll() {
		List<Race> lista = repository.findAll(); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada.");
        }
        return lista;
	}

	@Override
	public Race update(Race race) {
		findById(race.getId());
        return repository.save(race);
	}

	@Override
	public void delete(Integer id) {
		Race corrida = findById(id);
        repository.delete(corrida); 
	}

	@Override
	public List<Race> findByDate(ZonedDateTime date) {
		List<Race> lista = repository.findByDate(date);
	    if (lista.isEmpty()) {
	        throw new ObjectNotFound("Corrida não encontrada encontrada.");
	    }
	    return lista;
	}

	@Override
	public List<Race> findByDateBetween(ZonedDateTime date1, ZonedDateTime date2) {
		List<Race> lista = repository.findByDateBetween(date1, date2);
	    if (lista.isEmpty()) {
	        throw new ObjectNotFound("Nenhuma corrida foi encontrada entre a data selecionada");
	    }
	    return lista;
	}

	@Override
	public List<Race> findByTrackOrderByDate(Track track) {
		List<Race> lista = repository.findByTrackOrderByDate(track); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada na pista: %s".formatted(track.getName()));
        }
        return lista; 
	}

	@Override
	public List<Race> findByChampionshipOrderByDate(Championship championship) {
		List<Race> lista = repository.findByChampionshipOrderByDate(championship); 
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma corrida cadastrada no campeonato: %s".formatted(championship.getDescription()));
        }
        return lista;
	}

}