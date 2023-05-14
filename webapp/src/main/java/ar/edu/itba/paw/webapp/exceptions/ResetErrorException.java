package ar.edu.itba.paw.webapp.exceptions;

public class ResetErrorException extends RuntimeException{
    public ResetErrorException() {
        super();
    }

    public ResetErrorException(String message) {
        super(message);
    }
}
