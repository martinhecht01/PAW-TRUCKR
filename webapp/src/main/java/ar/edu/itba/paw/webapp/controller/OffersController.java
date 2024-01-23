package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class OffersController {

    private final TripServiceV2 ts;

    private final UserService us;
    private final static Logger LOGGER = LoggerFactory.getLogger(OffersController.class);


    @Autowired
    public OffersController(TripServiceV2 ts, UserService us) {
        this.ts = ts;
        this.us = us;
    }


//    @RequestMapping(value = "/offers/acceptOffer", method = { RequestMethod.POST })
//    public ModelAndView acceptOffer(@RequestParam("offerId") int offerId, @RequestParam("tripId") int tripId) {
//        ts.acceptProposal(offerId, LocaleContextHolder.getLocale());
//
//
//        Trip trip = ts.getTripOrRequestByIdAndUserId(tripId, getCurrentUser()).orElseThrow(TripOrRequestNotFoundException::new);
//
//        ModelAndView mav;
//
//        if(Objects.equals(getCurrentUser().getRole(), "TRUCKER")) {
//            mav = new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripId);
//        }
//        else {
//            mav = new ModelAndView("redirect:/trips/acceptSuccess?tripId=" + tripId);
//        }
//
//        LOGGER.info("Proposal with Id: {} accepted successfully", offerId);
//        mav.addObject("trip", trip);
//        return mav;
//    }

    @RequestMapping(value = "/offers/rejectOffer", method = RequestMethod.POST)
    public ModelAndView cancelOffer(@ModelAttribute("offerId") final String offerId, @ModelAttribute("tripId") final Integer tripId) {
        LOGGER.info("Cancelling offer with id {}", offerId);
        ts.deleteOffer(Integer.parseInt(offerId));
        ModelAndView mav;

        if(Objects.equals(getCurrentUser().getRole(), "TRUCKER")) {
            mav = new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripId);
        }
        else {
            mav = new ModelAndView("redirect:/requests/manageRequest?requestId=" + tripId);
        }
        return mav;
    }

//    @RequestMapping(value = "/offers/acceptCounterOffer", method = { RequestMethod.POST })
//    public ModelAndView acceptCounterOffer(@RequestParam("offerId") int offerId, @RequestParam("tripId") int tripid) {
//        ts.acceptCounterOffer(offerId);
//
//        Trip trip = ts.getTripOrRequestByIdAndUserId(tripid, getCurrentUser()).orElseThrow(TripOrRequestNotFoundException::new);
//
//        ModelAndView mav = new ModelAndView();
//        if(Objects.equals(getCurrentUser().getRole(), "TRUCKER")) {
//            mav = new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripid);
//            mav.addObject("trip", trip);
//        }
//        else {
//            mav = new ModelAndView("redirect:/requests/manageRequest?requestId=" + tripid);
//            mav.addObject("request", trip);
//        }
//
//        LOGGER.info("Counter Offer with Id: {} accepted successfully", offerId);
//        return mav;
//    }

    @RequestMapping(value = "/offers/rejectCounterOffer", method = { RequestMethod.POST })
    public ModelAndView rejectCounterOffer(@RequestParam("offerId") int offerId) {
        ts.rejectCounterOffer(offerId);

        LOGGER.info("Counter Offer with Id: {} rejected successfully", offerId);
        return new ModelAndView("redirect:/myOffers");
    }

//    @RequestMapping(value = "/offers/sendCounterOffer", method = { RequestMethod.POST })
//    public ModelAndView sendCounterOffer(@RequestParam("offerId") String offerId, @Valid @ModelAttribute("acceptForm") final AcceptForm form,  final BindingResult result) {
//        if(result.hasErrors())
//            return sendCounterOffer(form, offerId);
//        ts.sendCounterOffer(Integer.parseInt(offerId), getCurrentUser(), form.getDescription(), form.getPrice());
//
//        Proposal proposal = ts.getProposalById(Integer.parseInt(offerId)).orElseThrow(TripOrRequestNotFoundException::new);
//        Trip trip = ts.getTripOrRequestByIdAndUserId(proposal.getTrip().getTripId(), getCurrentUser()).orElseThrow(TripOrRequestNotFoundException::new);
//        ModelAndView mav;
//
//        if(Objects.equals(getCurrentUser().getRole(), "TRUCKER")) {
//            mav = new ModelAndView("redirect:/trips/manageTrip?tripId=" + trip.getTripId());
//            mav.addObject("trip", trip);
//        }
//        else {
//            mav = new ModelAndView("redirect:/requests/manageRequest?requestId=" + trip.getTripId());
//            mav.addObject("request", trip);
//        }
//        return mav;
//    }

    @RequestMapping("/offers/sendCounterOffer")
    public ModelAndView sendCounterOffer(@ModelAttribute("acceptForm") final AcceptForm form, @RequestParam("offerId") String offerId) {
        Proposal proposal = ts.getProposalById(Integer.parseInt(offerId)).orElseThrow(TripOrRequestNotFoundException::new);
        ModelAndView mav = new ModelAndView("user/sendCounterOffer");
        mav.addObject("proposal", proposal);
        return mav;
    }
//    @RequestMapping(path = "/offers/deleteCounterOffer", method = { RequestMethod.POST })
//    public ModelAndView deleteCounterOffer(@RequestParam("offerId") int offerId, @RequestParam("tripId") String tripId) {
//        ts.deleteCounterOffer(offerId);
//
//        LOGGER.info("Counter Offer with Id: {} deleted successfully", offerId);
//
//        Trip trip = ts.getTripOrRequestByIdAndUserId(Integer.parseInt(tripId), getCurrentUser()).orElseThrow(TripOrRequestNotFoundException::new);
//        ModelAndView mav;
//
//        if(Objects.equals(getCurrentUser().getRole(), "TRUCKER")) {
//            mav = new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripId);
//            mav.addObject("trip", trip);
//        }
//        else {
//            mav = new ModelAndView("redirect:/requests/manageRequest?requestId=" + tripId);
//            mav.addObject("request", trip);
//        }
//        return mav;
//    }

    public User getCurrentUser(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).get();
        }
        return null;
    }
}
