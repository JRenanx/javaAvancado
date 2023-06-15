package br.com.trier.springvespertino.jogoDeDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.trier.springvespertino.models.dto.ApostaDto;

@RequestMapping("/dados")
public class Dados {

    @GetMapping("/jogar/{qtDados}/{aposta}")
    public ResponseEntity<ApostaDto> jogar(@PathVariable int qtDados, @PathVariable int aposta) {
        ApostaDto ret = null;
        if (!validaDados(qtDados)) {
            ret = new ApostaDto(null, null, null, "invalido");
            return new ResponseEntity<ApostaDto>(ret, HttpStatus.NO_CONTENT);
        }
        if (!validaAposta(qtDados, aposta)) {
            ret = new ApostaDto(null, null, null, "invalido");
            return new ResponseEntity<ApostaDto>(ret, HttpStatus.NO_CONTENT);
        }

        int soma = 0;
        List<Integer> sorteados = new ArrayList<Integer>();
        for (int i = 0; i < qtDados; i++) {
            int valor = sorteia();
            sorteados.add(valor);
            soma += valor;
        }

        if (soma == aposta) {
            ret = new ApostaDto(sorteados, soma, null, "Acertou");
        } else {
            ret = new ApostaDto(sorteados, soma, percDif(aposta, soma), "Perdeu");
        }
        return ResponseEntity.ok(ret);

    }

    private int sorteia() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    private boolean validaDados(int qtDados) {
        return qtDados >= 1 && qtDados <= 4;
    }

    private boolean validaAposta(int qtDados, int aposta) {
        int min = qtDados;
        int max = qtDados * 6;
        if (aposta < min || aposta > max) {
            return false;
        }
        return true;

    }
}
