package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.stereotype.Component;

@Component
public class CuitNotAvailableException extends RuntimeException {
    public CuitNotAvailableException(){
        super("exception.CuitNotAvailable");
    }
}