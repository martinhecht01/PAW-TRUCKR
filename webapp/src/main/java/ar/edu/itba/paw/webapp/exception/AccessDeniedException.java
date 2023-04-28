package ar.edu.itba.paw.webapp.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(){
        super();
    }

    public AccessDeniedException(String message){
        super(message);
    }
}