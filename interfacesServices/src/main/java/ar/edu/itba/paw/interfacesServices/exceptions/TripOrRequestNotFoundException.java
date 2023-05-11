package ar.edu.itba.paw.interfacesServices.exceptions;

public class TripOrRequestNotFoundException extends RuntimeException{
    public TripOrRequestNotFoundException(){
        super();
    }

    public TripOrRequestNotFoundException(String message){
        super(message);
    }
}
