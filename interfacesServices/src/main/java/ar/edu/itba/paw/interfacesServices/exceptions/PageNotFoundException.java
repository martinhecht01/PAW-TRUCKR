package ar.edu.itba.paw.interfacesServices.exceptions;


public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(){
        super("exception.PageNotFound");
    }
}
