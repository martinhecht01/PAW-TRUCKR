package ar.edu.itba.paw.webapp.exception;

public class TripNotFoundException extends RuntimeException{
    public TripNotFoundException(){
        super();
    }

    public TripNotFoundException(String message){
        super(message);
    }
}
