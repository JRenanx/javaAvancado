package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.trier.springvespertino.models.dto.CountryDTO;

public class CountryResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<CountryDTO> getPais(String url) {
        return rest.getForEntity(url, CountryDTO.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<CountryDTO>> getPaises(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CountryDTO>>() {
        });
    }

}
