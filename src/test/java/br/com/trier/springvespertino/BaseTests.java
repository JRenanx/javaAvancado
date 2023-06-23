package br.com.trier.springvespertino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springvespertino.service.CampeonatoService;
import br.com.trier.springvespertino.service.CorridaService;
import br.com.trier.springvespertino.service.EquipeService;
import br.com.trier.springvespertino.service.PaisService;
import br.com.trier.springvespertino.service.PilotoService;
import br.com.trier.springvespertino.service.PistaService;
import br.com.trier.springvespertino.service.UserService;
import br.com.trier.springvespertino.service.impl.CampeonatoServiceImpl;
import br.com.trier.springvespertino.service.impl.CorridaServiceImpl;
import br.com.trier.springvespertino.service.impl.EquipeServiceImpl;
import br.com.trier.springvespertino.service.impl.PaisServiceImpl;
import br.com.trier.springvespertino.service.impl.PilotoServiceImpl;
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
    public PistaService pistaService() {
        return new PistaServiceImpl();
    }
    
    @Bean
    public CampeonatoService campeonatoService() {
        return new CampeonatoServiceImpl();
    }
    
    @Bean
    public CorridaService corridaService() {
        return new CorridaServiceImpl();
    }
    
    @Bean
    public EquipeService equipeService() {
        return new EquipeServiceImpl();
    }
    
    @Bean
    public PaisService paisService() {
        return new PaisServiceImpl();
    }
    
    @Bean
    public PilotoService pilotoService() {
        return new PilotoServiceImpl();
    }
  
}
