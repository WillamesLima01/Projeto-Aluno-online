package br.com.alunoonline.api.exception;

public class MatriculaNotFoundException extends RuntimeException{

    public MatriculaNotFoundException(String message){

        super(message);
    }
}
