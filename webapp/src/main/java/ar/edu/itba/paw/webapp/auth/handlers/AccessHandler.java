package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AccessHandler {

    @Autowired
    private UserService us;

    public boolean userAccessVerification(String id){
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (user != null) {
            return Integer.toString(user.getUserId()).equals(id);
        }
        return false;
    }
}
