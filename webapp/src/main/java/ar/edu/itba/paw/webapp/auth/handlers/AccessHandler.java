package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AccessHandler {

    @Autowired
    private UserService us;

    //TODO estas excepciones se ven como 500 en el server
    public boolean userAccessVerification(String id){
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (user != null) {
            return Integer.toString(user.getUserId()).equals(id);
        }
        return false;
    }
}
