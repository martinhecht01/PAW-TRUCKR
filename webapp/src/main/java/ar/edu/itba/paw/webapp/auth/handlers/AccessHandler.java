package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.util.Optional;


@Component
public class AccessHandler {

    private UserService us;
    private TripServiceV2 ts;

    @Autowired
    public AccessHandler(UserService us, TripServiceV2 ts){
        this.us = us;
        this.ts = ts;
    }

    public boolean userAccessVerification(String id) {
        User user = getLoggedUser();
        if (user == null) {
            return false;
        }
        return Integer.toString(user.getUserId()).equals(id);
    }

    public boolean userTripOwnerVerification(ReviewForm form){
        User user = getLoggedUser();
        if (user == null) {
            return false;
        }

        Trip trip = ts.getTripOrRequestById(form.getTripId()).orElseThrow(BadRequestException::new);
        if (trip != null) {
            return trip.getTrucker().getUserId().equals(user.getUserId()) || trip.getProvider().getUserId().equals(user.getUserId());
        }
        return false;
    }

    public boolean getAuth(){
        User user = getLoggedUser();
        return user != null;
    }

    public boolean isTripOwner(Integer tripId){
        User user = getLoggedUser();
        if (user == null) {
            return false;
        }
        Optional<Trip> trip = ts.getTripOrRequestById(tripId);
        return trip.isPresent() &&
                ((trip.get().getTrucker() != null && user.getUserId().equals(trip.get().getTrucker().getUserId())) ||
                        (trip.get().getProvider() != null && user.getUserId().equals(trip.get().getProvider().getUserId())));
    }

    private User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser") || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            return null;
        }
        Optional<User> optUser = us.getCurrentUser();
        return optUser.orElse(null);
    }
}
