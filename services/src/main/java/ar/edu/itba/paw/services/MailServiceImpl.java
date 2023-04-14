package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

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
        message1.setText("El viaje fue aceptado por: "+ accepted.getName());
        message1.setSubject("Tu viaje ha sido aceptado");

        mailSender.send(message1);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("tomigayba02yt@gmail.com");
        message2.setTo(accepted.getEmail());
        message2.setText("El viaje ha sido confirmado por:  "+ trucker.getName()+"\n Datos del viaje" +
                "\nPatente"+ trip.getLicensePlate()+"\nOrigen:"+ trip.getOrigin()+"-  "+trip.getDepartureDate()
                + "\nDestino:" +trip.getDestination() +"-  " + trip.getArrivalDate() + "\n");


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
