package ar.edu.itba.paw.webapp.exceptions;


import org.springframework.stereotype.Component;

@Component
public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(){
        super("exception.TripNotFound");
    }
}
