package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PilotoCorridaDTO {
    private Integer id;
    private Integer colocacao;
    private Integer pilotoId;
    private String pilotoName;
    private Integer corridaId;
    private String corridaData;
}