package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.stereotype.Component;

@Component
public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(){
        super("exeption.OfferNotFound");
    }
}