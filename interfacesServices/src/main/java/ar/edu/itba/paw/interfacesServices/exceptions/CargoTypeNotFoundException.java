package ar.edu.itba.paw.interfacesServices.exceptions;

public class CargoTypeNotFoundException extends RuntimeException{
    public CargoTypeNotFoundException() {
        super("exception.CargoTypeNotFound");
    }
}
