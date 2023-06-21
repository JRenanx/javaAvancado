package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.trier.springvespertino.models.dto.UserDTO;

public class PaisResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<UserDTO> getUser(String url) {
        return rest.getForEntity(url, UserDTO.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<UserDTO>> getUsers(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
        });
    }


}
