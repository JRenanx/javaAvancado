package br.com.trier.springvespertino.recources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exemplos")
public class Exemplos {

    @GetMapping // anotação usada para tratar uma solicitação da operação referente ao verbo HTTP GET
   public String exemplo() {
       return "Bem vindo!";
   }
    
    @GetMapping("/ex2")
    public String exemplo1() {
        return "Hello World!";
    }
    
    @GetMapping("/ex3")
    public String exemplo1(@RequestParam String sexo) {
        if(sexo.equalsIgnoreCase("M")) {
            return "Bem vindo!";
        }
        return "Bem vinda!";
    }
    
    @GetMapping("/ex4")
    public String exemplo1(@RequestParam String sexo, @RequestParam String nome) //valor passado para a API como parâmetros na requisição: http://localhost:8080/users?id=1&nome=ABC
    {
        if(sexo.equalsIgnoreCase("M")) {
            return "Bem vindo: " + nome;
        }
        return "Bem vinda!" + nome;
    }
    
    @GetMapping("/somar/{n1}/{n2}") //pega o valores da app
    public int somar(@PathVariable int n1, @PathVariable int n2) {
        return n1 + n2;
    }
}
