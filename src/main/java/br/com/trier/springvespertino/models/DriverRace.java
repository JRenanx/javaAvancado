package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.DriverRaceDTO;
import br.com.trier.springvespertino.utils.DataUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "driver_race")
public class DriverRace {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_driver_race")
    private Integer id;
    
    @Column(name = "position_driver_race")
    private Integer position;

    @ManyToOne
    @NotNull
    private Driver driver;

    @ManyToOne
    @NotNull
    private Race race;

    public DriverRace(DriverRaceDTO racePilotDTO, Driver driver, Race race) {
        this(racePilotDTO.getId(), racePilotDTO.getPosition() , driver, race);
    }

    public DriverRaceDTO toDTO() {
        return new DriverRaceDTO(id, driver.getId(), driver.getName(), race.getId(),
                DataUtils.zoneDateToBrDate(race.getDate()), position);
    }

}