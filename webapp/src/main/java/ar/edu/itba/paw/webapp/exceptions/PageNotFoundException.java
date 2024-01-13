package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.stereotype.Component;

@Component
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(){
        super("exception.PageNotFound");
    }
}
