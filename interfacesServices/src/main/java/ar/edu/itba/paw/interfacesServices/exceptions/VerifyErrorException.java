package ar.edu.itba.paw.interfacesServices.exceptions;

public class VerifyErrorException extends Exception {
    public VerifyErrorException() {
        super();
    }

    public VerifyErrorException(String message) {
        super(message);
    }
}
