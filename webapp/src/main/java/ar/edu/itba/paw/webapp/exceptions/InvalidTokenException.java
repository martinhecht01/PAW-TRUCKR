package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(){
        super("exception.InvalidToken");
    }
}