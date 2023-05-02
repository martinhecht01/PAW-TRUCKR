package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    private String generateEmailContent() {
        Context context = new Context();
        context.setVariable("title", "My Email Title");
        context.setVariable("message", "Hello, this is my email message.");
        return templateEngine.process("emailTemplate.html", context);
    }

    private String generateEmailConfirmation(User confirmed) {
        Context context = new Context();
        context.setVariable("user", confirmed);
        return templateEngine.process("emailconfirmation.html", context);
    }

    public void sendConfirmationEmail(User user) throws MessagingException {
        String htmlContent = generateEmailConfirmation(user);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Account Confirmation");
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
    private String generateTripConfirmation(User user, Trip confirmed) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("trip", confirmed);
        return templateEngine.process("tripconfirmation.html", context);
    }

    public void sendTripEmail(User user,Trip trip) throws MessagingException {
        String htmlContent = generateTripConfirmation(user,trip);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Trip confirmation");
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
    private String generateProposal(User user, Proposal proposal) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("proposal", proposal);
        return templateEngine.process("proposal.html", context);
    }

    public void sendProposalEmail(User user,Proposal proposal) throws MessagingException {
        String htmlContent = generateProposal(user,proposal);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Trip Proposal!");
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }



    @Override
    public void sendEmail(String toEmailAddress){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tomigayba02yt@gmail.com");
        message.setTo(toEmailAddress);
        message.setText("Hola! Te has registrado en CoTruck!");
        message.setSubject("Ya estas registrado en CoTruck.");

        mailSender.send(message);
    }

    public void sendEmailTrip(User trucker, User accepted, Trip trip){
        SimpleMailMessage message1 = new SimpleMailMessage();
        message1.setFrom("tomigayba02yt@gmail.com");
        message1.setTo(trucker.getEmail());
        message1.setText("El viaje fue aceptado por: "+ accepted.getName()+ "\n Datos del viaje" +
                "\nPatente:  "+ trip.getLicensePlate()+"\nOrigen: "+ trip.getOrigin()+"  -  "+trip.getDepartureDate()
                + "\nDestino:" +trip.getDestination() +"  -  " + trip.getArrivalDate() + "\n");
        message1.setSubject("Tu viaje ha sido aceptado");

        mailSender.send(message1);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("tomigayba02yt@gmail.com");
        message2.setTo(accepted.getEmail());
        message2.setText("El viaje ha sido confirmado por:  "+ trucker.getName()+"\n Datos del viaje" +
                "\nPatente:  "+ trip.getLicensePlate()+"\nOrigen: "+ trip.getOrigin()+"  -  "+trip.getDepartureDate()
                + "\nDestino:" +trip.getDestination() +"  -  " + trip.getArrivalDate() + "\n" +
                "Precio a cancelar:  " + trip.getPrice());


        message2.setSubject("Viaje confirmado");
//        String email,
//        String name,
//        String id,
//        String licensePlate,
//        int availableWeight,
//        int availableVolume,
//        LocalDateTime departureDate,
//        LocalDateTime arrivalDate,
//        String origin,
//        String destination,
//        String type

        mailSender.send(message2);
    }


}
