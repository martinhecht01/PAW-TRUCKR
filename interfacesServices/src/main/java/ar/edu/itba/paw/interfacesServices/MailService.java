package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.*;


public interface MailService {

    void sendProposalEmail(User user, Proposal proposal);
    void sendTripEmail(User user,User user2,Trip trip);
    void sendConfirmationEmail(User user);
    void sendResetEmail(User user,Integer hash);
    void sendCompletionEmail(User user, Trip trip);
    void sendStatusEmail(User user, Trip trip);
    void sendSecureTokenEmail(User user, Integer tokenValue);
}
