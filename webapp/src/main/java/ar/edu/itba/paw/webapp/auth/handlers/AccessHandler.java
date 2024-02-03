package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.util.Objects;
import java.util.Optional;


@Component
public class AccessHandler {

    private UserService us;
    private TripServiceV2 ts;
    private AlertService as;

    @Autowired
    public AccessHandler(UserService us, TripServiceV2 ts, AlertService as){
        this.us = us;
        this.ts = ts;
        this.as = as;
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

    public boolean canCreateOffer(Integer tripId, Integer parent_offer_id){
        User user = getLoggedUser();
        if (user == null || tripId == null) {
            return false;
        }
        boolean isTripOwner = isTripOwner(tripId);
        if(parent_offer_id != null){
            // es una contraoferta
            return isTripOwner;
        }
        //es una oferta
        Trip trip = ts.getTripOrRequestById(tripId).orElseThrow(BadRequestException::new);

        if(trip.getTrucker() != null){
            //ya tiene un camionero asignado
            if(Objects.equals(user.getRole(), RoleType.TRUCKER.getRoleName())){
                return false;
            }
        }else{
            //ya tiene un proveedor asignado
            if(Objects.equals(user.getRole(), RoleType.PROVIDER.getRoleName())){
                return false;
            }
        }
        return !isTripOwner;
    }

    public boolean isAlertOwner(Integer alertId){
        User user = getLoggedUser();
        if (user == null) {
            return false;
        }
        Optional<Alert> alert = as.getAlertById(alertId);
        return alert.isPresent() && user.getUserId().equals(alert.get().getUser().getUserId());
    }

    public boolean canSeeOffer(Integer offerId){
        User user = getLoggedUser();
        if (user == null)
            return false;

        Optional<Proposal> offer = ts.getProposalById(offerId);

        if (offer.isPresent()) {
            Trip trip = offer.get().getTrip();
            return offer.get().getUser().getUserId().equals(user.getUserId())
                    || ((trip.getTrucker() != null && user.getUserId().equals(trip.getTrucker().getUserId())) ||
                            (trip.getProvider() != null && user.getUserId().equals(trip.getProvider().getUserId())))
                    || (offer.get().getProposalId() != 0 && ts.getProposalById(offer.get().getProposalId()).orElseThrow(ProposalNotFoundException:: new).getUser().equals(user));
        }
        return false;
    }

    public boolean canActOnOffer(int offerId) {
        User user = getLoggedUser();
        if (user == null)
            return false;

        Proposal offer = ts.getProposalById(offerId).orElseThrow(BadRequestException::new);

        if(offer.getParentProposal() == null)
            return isTripOwner(offer.getTrip().getTripId());
        return offer.getParentProposal().getUser().getUserId().equals(user.getUserId());
    }

    public boolean isOfferOwner(int offerId){
        User user = getLoggedUser();
        if (user == null)
            return false;

        Proposal offer = ts.getProposalById(offerId).orElseThrow(BadRequestException::new);
        return offer.getUser().getUserId().equals(user.getUserId());
    }

    public boolean canSeeOffers(Integer tripId){
        if(tripId != null){
            return isTripOwner(tripId);
        }
        return true;
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
