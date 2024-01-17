package ar.edu.itba.paw.interfacesServices.exceptions;


public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(){
        super("exception.OfferNotFound");
    }
}