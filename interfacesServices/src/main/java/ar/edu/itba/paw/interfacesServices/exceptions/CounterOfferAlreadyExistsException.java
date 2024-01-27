package ar.edu.itba.paw.interfacesServices.exceptions;

public class CounterOfferAlreadyExistsException extends RuntimeException{
    public CounterOfferAlreadyExistsException() {
        super("exception.CounterOfferAlreadyExists");
    }
}
