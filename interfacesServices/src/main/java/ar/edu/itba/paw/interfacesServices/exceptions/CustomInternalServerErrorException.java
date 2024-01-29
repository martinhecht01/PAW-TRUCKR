package ar.edu.itba.paw.interfacesServices.exceptions;

public class CustomInternalServerErrorException extends RuntimeException{

    public CustomInternalServerErrorException(String message) {
        super(message);
    }
}
