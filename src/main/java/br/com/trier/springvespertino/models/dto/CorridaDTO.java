package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorridaDTO {
    
    private Integer id;
    private String date;
    private Integer Pista;
    private String pistaName;;
    private Integer campeonatoId;
    private String campeonatoName;
    

}
