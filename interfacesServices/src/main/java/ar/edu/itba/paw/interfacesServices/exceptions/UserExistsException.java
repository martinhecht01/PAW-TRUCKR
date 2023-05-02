package ar.edu.itba.paw.interfacesServices.exceptions;

public class UserExistsException extends Exception{
    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
