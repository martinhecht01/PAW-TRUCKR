package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.*;

import java.util.Locale;


public interface MailService {

    void sendProposalEmail(User user,Proposal proposal, Locale locale);
    void sendTripEmail(User user,User user2,Trip trip, Locale locale);
    void sendConfirmationEmail(User user, Locale locale);
    void sendResetEmail(User user,Integer hash, Locale locale);
    void sendCompletionEmail(User user, Trip trip,Locale locale);
    void sendStatusEmail(User user, Trip trip,Locale locale);
    void sendSecureTokenEmail(User user, Integer tokenValue, Locale locale);
}
