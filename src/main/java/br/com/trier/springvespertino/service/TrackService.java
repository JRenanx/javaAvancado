package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Track;

public interface TrackService {

    Track findById(Integer id);

    Track insert(Track user);

    Track update(Track user);

    void delete(Integer id);

    List<Track> listAll();

    List<Track> findByNameStartsWithIgnoreCase(String name);

    List<Track> findBySizeBetween(Integer sizeIn, Integer sizeFin);

    List<Track> findByCountryOrderBySizeDesc(Country country);

}
