package br.com.trier.springvespertino.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CorridaPaisAnoDTO {

    
    private Integer year;
    private String pais;
    private Integer corridaSize;
    private List<CorridaDTO> corridas;
}
