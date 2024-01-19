package ar.edu.itba.paw.interfacesServices.exceptions;

public class ReviewNotFoundException extends RuntimeException{

        public ReviewNotFoundException(){
            super("exception.ReviewNotFound");
        }
}
