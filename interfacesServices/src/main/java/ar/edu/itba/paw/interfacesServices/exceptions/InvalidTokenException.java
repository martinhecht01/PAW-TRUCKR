package ar.edu.itba.paw.interfacesServices.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(){
        super("exception.InvalidToken");
    }
}