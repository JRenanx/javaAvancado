package br.com.trier.springvespertino.service;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Track;

public interface RaceService
{

    Race findById(Integer id);
    
    Race insert(Race race);
    
    Race update(Race race);
    
    void delete(Integer id);
    
    List<Race> listAll();
    
    List<Race> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

    List<Race> findByDate(ZonedDateTime date);

    List<Race> findByTrackOrderByDate(Track track);

    List<Race> findByChampionshipOrderByDate(Championship championship);  
}