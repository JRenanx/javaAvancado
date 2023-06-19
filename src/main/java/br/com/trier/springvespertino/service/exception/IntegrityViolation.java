package br.com.trier.springvespertino.service.exception;

public class IntegrityViolation  extends RuntimeException{

    public IntegrityViolation(String message) {
        super (message);
    }
}
