package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.mail.Message;

@Component
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
        super("exception.UserNotFound");
    }
}
