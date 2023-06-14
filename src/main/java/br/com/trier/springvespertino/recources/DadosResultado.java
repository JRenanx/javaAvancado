package br.com.trier.springvespertino.recources;

import java.util.List;

import br.com.trier.springvespertino.models.Dados;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosResultado {
    private List<Dados> dadosList;
    private int soma;
    private double apostaPorcentagem;
    private double dadosPorcentagem;
}
