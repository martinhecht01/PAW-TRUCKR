package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.*;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.controller.UserControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class ReviewController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final ReviewService revs;
    private final UserService us;

    @Autowired
    public ReviewController(final ReviewService revs, final UserService us) {
        this.revs = revs;
        this.us = us;
    }

    @RequestMapping(value="/reviews/sendReview", method = { RequestMethod.POST })
    public ModelAndView sendReview(@RequestParam("tripid") int tripid, @RequestParam("userid") int userid, @RequestParam ("rating") int rating, @RequestParam("description") String comment){
        User user = getUser();
        revs.createReview(tripid, userid, rating, comment);
        if (Objects.equals(user.getRole(), "TRUCKER"))
            return new ModelAndView("redirect:/trips/manageTrip?tripId="+ tripid);
        else
            return new ModelAndView("redirect:/requests/manageRequest?requestId="+ tripid);
    }

    private User getUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof AuthUserDetailsImpl){
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).orElseThrow(UserNotFoundException::new);
        }
        return null;
    }

}
