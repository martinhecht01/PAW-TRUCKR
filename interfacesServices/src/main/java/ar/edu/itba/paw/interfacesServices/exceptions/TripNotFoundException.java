package ar.edu.itba.paw.interfacesServices.exceptions;


public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(){
        super("exception.TripNotFound");
    }
}
