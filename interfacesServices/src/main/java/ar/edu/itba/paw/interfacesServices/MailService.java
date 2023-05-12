package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.*;

import javax.mail.MessagingException;


public interface MailService {

    void sendProposalEmail(User user, Proposal proposal);
    void sendTripEmail(User user,User user2,Trip trip);
    void sendConfirmationEmail(User user);
    void sendResetEmail(User user,Integer hash);
    void sendProposalRequestEmail(User user, Proposal proposal);
    void sendRequestEmail(User user,User user2, Trip request);
    void sendSecureTokenEmail(User user, Integer tokenValue);
}
