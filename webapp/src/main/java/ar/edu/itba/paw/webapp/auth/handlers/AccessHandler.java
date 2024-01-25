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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser") || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            return false;
        }
        Optional<User> optUser = us.getCurrentUser();
        if (!optUser.isPresent())
            return false;
        User user = optUser.get();
        return Integer.toString(user.getUserId()).equals(id);
    }

    public boolean userTripOwnerVerification(ReviewForm form){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (auth == null || auth.getPrincipal().equals("anonymousUser") || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            return false;
        }
        Optional<User> optUser = us.getCurrentUser();
        if (!optUser.isPresent())
            return false;
        User user = optUser.get();

        Trip trip = ts.getTripOrRequestById(form.getTripId()).orElseThrow(BadRequestException::new);
        if (trip != null) {
            return trip.getTrucker().getUserId().equals(user.getUserId()) || trip.getProvider().getUserId().equals(user.getUserId());
        }
        return false;
    }

    public boolean isAuthenticated(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        Optional<User> user = us.getCurrentUser();
        return !user.isPresent();
    }

    public boolean queryUserVerification(String userId, String tripType){
        if (userId == null)
            return true;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (user != null) {
            if (Integer.toString(user.getUserId()).equals(userId))
                return true;
            return tripType.equals("publication");
        }
        return false;
    }
}
