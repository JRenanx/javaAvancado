package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.DriverDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "driver")
public class Driver {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_driver")
    private Integer id;
    
    @Column(name = "name_driver")
    private String name;
    
    @ManyToOne
    private Country country;
    
    @ManyToOne
    private Team team;
      
    
    public Driver(DriverDTO dto, Country country, Team team) {
        this(dto.getId(), dto.getName(), country, team);
    }
    
    public DriverDTO toDTO() {
        return new DriverDTO(id, name, country.getId(), country.getName(), team.getId(), team.getName());
    }
}
