package br.com.trier.springvespertino.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DriverRaceDTO {
    
    private Integer id;
    
    private Integer driverId;
    
    private String driverName;
    
    private Integer raceId;
    
    private String raceDate;
    
    private Integer position;

}