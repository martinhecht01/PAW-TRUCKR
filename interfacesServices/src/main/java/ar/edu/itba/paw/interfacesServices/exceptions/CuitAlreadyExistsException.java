package ar.edu.itba.paw.interfacesServices.exceptions;

public class CuitAlreadyExistsException extends RuntimeException{
    public CuitAlreadyExistsException() {
        super("exception.CuitAlreadyExists");
    }
}
