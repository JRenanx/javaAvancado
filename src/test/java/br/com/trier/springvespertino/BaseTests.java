package br.com.trier.springvespertino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.UserService;
import br.com.trier.springvespertino.service.impl.PistaServiceImpl;
import br.com.trier.springvespertino.service.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests  {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    
    @Bean
    public PistaService pistaSerive() {
        return new PistaServiceImpl();
    }
}
