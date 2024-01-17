package ar.edu.itba.paw.interfacesServices.exceptions;


public class CuitNotAvailableException extends RuntimeException {
    public CuitNotAvailableException(){
        super("exception.CuitNotAvailable");
    }
}