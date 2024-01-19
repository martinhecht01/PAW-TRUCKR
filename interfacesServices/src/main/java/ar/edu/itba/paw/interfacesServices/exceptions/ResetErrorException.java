package ar.edu.itba.paw.interfacesServices.exceptions;

public class ResetErrorException extends RuntimeException {
    public ResetErrorException() {
        super("exception.ResetError");
    }

    public ResetErrorException(String message) {
        super(message);
    }

}
