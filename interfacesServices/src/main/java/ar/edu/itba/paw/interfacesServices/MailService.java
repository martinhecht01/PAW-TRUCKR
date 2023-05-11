package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.*;

import javax.mail.MessagingException;


public interface MailService {

    void sendProposalEmail(User user, Proposal proposal) throws MessagingException;
    void sendTripEmail(User user,User user2,Trip trip) throws MessagingException;
    void sendConfirmationEmail(User user) throws MessagingException;
    void sendResetEmail(User user,Integer hash) throws MessagingException;
    void sendProposalRequestEmail(User user, Proposal proposal) throws MessagingException;
    void sendRequestEmail(User user,User user2, Trip request) throws MessagingException;
    void sendSecureTokenEmail(User user, Integer tokenValue) throws MessagingException;
}
