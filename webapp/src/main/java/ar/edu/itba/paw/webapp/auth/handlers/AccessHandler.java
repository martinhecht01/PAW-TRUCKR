package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Optional;


@Component
public class AccessHandler {

    @Autowired
    private UserService us;

    @Autowired
    private TripServiceV2 ts;

    public boolean userAccessVerification(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (user != null) {
            return Integer.toString(user.getUserId()).equals(id);
        }
        return false;
    }

    public boolean userTripOwnerVerification(ReviewForm form){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Trip trip = ts.getTripOrRequestById(form.getTripId()).orElseThrow(UserNotFoundException::new);

        if (user != null && trip != null) {
            return trip.getTrucker().getUserId().equals(user.getUserId()) || trip.getProvider().getUserId().equals(user.getUserId());
        }
        return false;
    }
}
