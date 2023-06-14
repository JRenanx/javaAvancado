package br.com.trier.springvespertino.recources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Dados;

@Service
public class DadosService {
    private static final int MIN_DADO = 1;
    private static final int MAX_DADO = 4;
    private static final int MIN_DADO_VALOR = 1;
    private static final int MAX_DADO_VALOR = 6;

    public DadosResultado lancaDado(int numDado, int apostaValor) {
        if (numDado < MIN_DADO || numDado > MAX_DADO) {
   
        }

        if (apostaValor < numDado * MIN_DADO_VALOR || apostaValor > numDado * MAX_DADO_VALOR) {
           
        }

        Random random = new Random();
        List<Dados> listaDados = new ArrayList<>();
        int soma = 0;

        for (int i = 0; i < numDado; i++) {
            int valorDados = random.nextInt(MAX_DADO_VALOR) + 1;
            Dados dados = new Dados();
            dados.setDadosNumero(i + 1);
            dados.setDadosValores(valorDados);
            listaDados.add(dados);
            soma += valorDados;
        }

        double apostaPorcento = ((double) soma / apostaValor) * 100;
        double dadosPorcento = 100.0 / numDado;

        return new DadosResultado(listaDados, soma, apostaPorcento, dadosPorcento);
    }
}
