package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.stereotype.Component;

@Component
public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException(){
        super("exception.UserAlreadyVerified");
    }
}
