package br.com.trier.springvespertino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springvespertino.service.ChampionshipService;
import br.com.trier.springvespertino.service.RaceService;
import br.com.trier.springvespertino.service.TeamService;
import br.com.trier.springvespertino.service.CountryService;
import br.com.trier.springvespertino.service.DriverService;
import br.com.trier.springvespertino.service.TrackService;
import br.com.trier.springvespertino.service.UserService;
import br.com.trier.springvespertino.service.impl.ChampionshipServiceImpl;
import br.com.trier.springvespertino.service.impl.RaceServiceImpl;
import br.com.trier.springvespertino.service.impl.TeamServiceImpl;
import br.com.trier.springvespertino.service.impl.CountryServiceImpl;
import br.com.trier.springvespertino.service.impl.DriverServiceImpl;
import br.com.trier.springvespertino.service.impl.TrackServiceImpl;
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
    public TrackService pistaService() {
        return new TrackServiceImpl();
    }
    
    @Bean
    public ChampionshipService campeonatoService() {
        return new ChampionshipServiceImpl();
    }
    
    @Bean
    public RaceService corridaService() {
        return new RaceServiceImpl();
    }
    
    @Bean
    public TeamService equipeService() {
        return new TeamServiceImpl();
    }
    
    @Bean
    public CountryService paisService() {
        return new CountryServiceImpl();
    }
    
    @Bean
    public DriverService pilotoService() {
        return new DriverServiceImpl();
    }
  
}
