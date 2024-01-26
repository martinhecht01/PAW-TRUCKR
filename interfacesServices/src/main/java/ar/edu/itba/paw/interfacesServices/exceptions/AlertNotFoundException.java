package ar.edu.itba.paw.interfacesServices.exceptions;

public class AlertNotFoundException extends RuntimeException{
    public AlertNotFoundException() {
        super("exception.AlertNotFound");
    }


}
