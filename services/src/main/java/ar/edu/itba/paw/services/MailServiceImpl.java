package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


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

}
