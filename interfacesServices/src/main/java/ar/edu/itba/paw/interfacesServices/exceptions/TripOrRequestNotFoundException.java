package ar.edu.itba.paw.interfacesServices.exceptions;

public class TripOrRequestNotFoundException extends RuntimeException{

    public TripOrRequestNotFoundException(){
        super("exception.TripNotFound");
    }
}
