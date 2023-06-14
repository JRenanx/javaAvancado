package br.com.trier.springvespertino.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Dados {

    @Id
    private Integer id;
    private Integer DadosNumero;
    private Integer DadosValores;
}