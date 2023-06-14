package br.com.trier.springvespertino.recources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dados")
public class DadosController {
    private final DadosService dadosService;

    @Autowired
    public DadosController(DadosService dadosService) {
        this.dadosService = dadosService;
    }

    @PostMapping("/lancaDados")
    public ResponseEntity<DadosResultado> launchDice(@RequestParam("numDado") int numDado, @RequestParam("apostaValor") int apostaValor) {
        try {
            DadosResultado result = dadosService.lancaDado(numDado, apostaValor);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}