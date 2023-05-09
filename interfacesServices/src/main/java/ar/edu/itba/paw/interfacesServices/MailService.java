package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.*;

import javax.mail.MessagingException;


public interface MailService {

    public void sendProposalEmail(User user, Proposal proposal) throws MessagingException;
    public void sendTripEmail(User user,User user2,Trip trip) throws MessagingException;
    void sendConfirmationEmail(User user) throws MessagingException;
    public void sendResetEmail(User user,Integer hash) throws MessagingException;
    void sendProposalRequestEmail(User user, Proposal proposal) throws MessagingException;

    public void sendRequestEmail(User user,User user2, Trip request) throws MessagingException;

}
