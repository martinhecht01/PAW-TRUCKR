package ar.edu.itba.paw.interfacesServices.exceptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException() {
        super("exception.CityNotFound");
    }
}
