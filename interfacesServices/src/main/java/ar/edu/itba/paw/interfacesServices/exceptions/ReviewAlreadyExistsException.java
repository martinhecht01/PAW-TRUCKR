package ar.edu.itba.paw.interfacesServices.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException{

    public ReviewAlreadyExistsException(){
        super("exception.ReviewAlreadyExists");
    }

}
