package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RaceDTO {
    
    private Integer id;
    private String date;
    private Integer track;
    private String trackName;;
    private Integer championshipId;
    private String championshipName;
    

}
