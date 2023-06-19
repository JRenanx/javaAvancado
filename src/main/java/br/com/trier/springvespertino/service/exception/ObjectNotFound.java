package br.com.trier.springvespertino.service.exception;

public class ObjectNotFound extends RuntimeException{

    public ObjectNotFound (String message) {
        super(message);
    }
}
