package br.com.trier.springvespertino.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.repositories.UserRepository;
import br.com.trier.springvespertino.service.UserService;
import br.com.trier.springvespertino.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    
    private void findByEmail(User user) {
        Optional<User> existingUser = repository.findByEmail(user.getEmail());
<<<<<<< HEAD
        
         existingUser.ifPresent(u -> {
                if (!u.getId().equals(user.getId())) {
                    throw new IntegrityViolation("Email já existente: " + user.getEmail());
                }
            });
=======

        existingUser.ifPresent(u -> {
            if (!u.getId().equals(user.getId())) {
                throw new IntegrityViolation("Email já existente: " + user.getEmail());
            }
        });
>>>>>>> 2ad4cefe058665be43e82e5151220a9d2cfbefa2
    }
    
    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFound("O usuário %s não existe".formatted(id)));
    }

    @Override
    public User insert(User user) {
        findByEmail(user);
        return repository.save(user);
    }

    @Override
    public List<User> listAll() {
        List<User> lista = repository.findAll();
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum usuário cadastrado");
        }
        return lista;
    }

    @Override
    public User update(User user) {
        findById(user.getId());
        findByEmail(user);
        return repository.save(user);
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        repository.delete(user);    
    }

    @Override
    public List<User> findByNameStartsWithIgnoreCase(String name) {
        List<User> lista = repository.findByNameStartsWithIgnoreCase(name);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum usário inicia com %s".formatted(name));
        }
        return lista;
    }

}