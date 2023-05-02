package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import javax.mail.MessagingException;


public interface MailService {

    public void sendEmail(String toEmailAddress);

    public void sendEmailTrip(User trucker, User accepted, Trip trip);
    public void sendProposalEmail(User user, Proposal proposal) throws MessagingException;
    public void sendTripEmail(User user,Trip trip) throws MessagingException;
    void sendConfirmationEmail(User user) throws MessagingException;
    public void sendResetEmail(User user,Integer hash) throws MessagingException;

}
