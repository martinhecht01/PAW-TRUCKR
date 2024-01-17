package ar.edu.itba.paw.interfacesServices.exceptions;


public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException(){
        super("exception.UserAlreadyVerified");
    }
}
