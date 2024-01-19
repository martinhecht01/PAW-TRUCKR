package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthErrorException extends AuthenticationException {

        public AuthErrorException() {
            super("exception.AuthError");
        }

        public AuthErrorException(String message){
            super(message);
        }
}
