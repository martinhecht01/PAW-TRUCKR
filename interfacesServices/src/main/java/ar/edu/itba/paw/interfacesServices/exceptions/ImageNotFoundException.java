package ar.edu.itba.paw.interfacesServices.exceptions;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(){
        super("exception.ImageNotFound");
    }
}
