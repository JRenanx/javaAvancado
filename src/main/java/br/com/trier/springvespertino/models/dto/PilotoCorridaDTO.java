package br.com.trier.springvespertino.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PilotoCorridaDTO {
    
    private Integer id;
    
    private Integer pilotoId;
    
    private String pilotoName;
    
    private Integer corridaId;
    
    private String corridaDate;
    
    private Integer position;

}