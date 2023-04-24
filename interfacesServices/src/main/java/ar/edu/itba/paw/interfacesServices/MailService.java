package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import javax.mail.MessagingException;


public interface MailService {

    public void sendEmail(String toEmailAddress);

    public void sendEmailTrip(User trucker, User accepted, Trip trip);

    void sendConfirmationEmail(User user) throws MessagingException;

}
