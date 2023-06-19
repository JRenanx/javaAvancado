package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.User;

public interface UserService {

    User findById(Integer id);

    User insert(User user);

    List<User> listAll();

    User update(User user);

    void delete(Integer id);
    
    List<User> findByName(String name);

}