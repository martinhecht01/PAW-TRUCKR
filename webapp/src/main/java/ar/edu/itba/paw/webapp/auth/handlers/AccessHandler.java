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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return false;
        }

        Optional<AuthUserDetailsImpl> principal = Optional.of((AuthUserDetailsImpl) auth.getPrincipal());
        User user = principal.flatMap(pawAuthUserDetails -> us.getUserByCuit(pawAuthUserDetails.getUsername())).orElse(null);

        if (user != null) {
            return Integer.toString(user.getUserId()).equals(id);
        }
        return false;
    }
}
