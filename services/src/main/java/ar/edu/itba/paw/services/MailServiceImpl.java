package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailServiceImpl implements MailService {

    Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    private String generateEmailConfirmation(User confirmed) {
        Context context = new Context();
        context.setVariable("user", confirmed);
        return templateEngine.process("emailconfirmation.html", context);
    }
    @Async
    @Override
    public void sendConfirmationEmail(User user){
        String htmlContent = generateEmailConfirmation(user);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            LOGGER.info("Preparing confirmation email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Account Confirmation");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending confirmation email to: " + user.getEmail());
        }

        LOGGER.info("Sending confirmation email to: " + user.getEmail());
        mailSender.send(message);
    }
    private String generateTripConfirmation(User user,User user2, Trip confirmed) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("user2", user2);
        context.setVariable("trip", confirmed);
        return templateEngine.process("tripconfirmation.html", context);
    }

    @Async
    @Override
    public void sendTripEmail(User user,User user2,Trip trip){
        String htmlContent = generateTripConfirmation(user,user2,trip);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            LOGGER.info("Preparing trip email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Trip confirmation");
            helper.setText(htmlContent, true);
        }catch (MessagingException e) {
            LOGGER.error("Error while sending trip email to: " + user.getEmail());
        }

        mailSender.send(message);
    }
    private String generateTripCompletion(User user, Trip completed) {
        Context context = new Context();
        context.setVariable("user", user);

        context.setVariable("trip", completed);
        return templateEngine.process("tripcomplete.html", context);
    }
    //@Async
    @Override
    public void sendCompletionEmail(User user, Trip trip){
        String htmlContent = generateTripCompletion(user,trip);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Trip completed");
            helper.setText(htmlContent, true);
        }catch (MessagingException e) {
            LOGGER.error("Error while sending completion email to: " + user.getEmail());
        }
        LOGGER.info("Sending completion email to: " + user.getEmail());
        mailSender.send(message);
    }
    private String generateTripStatus(User user, Trip completed) {
        Context context = new Context();
        context.setVariable("user", user);

        context.setVariable("trip", completed);
        return templateEngine.process("statusupdate.html", context);
    }
    @Override
    @Async
    public void sendStatusEmail(User user, Trip trip){
        String htmlContent = generateTripStatus(user,trip);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Trip Status Changed");
            helper.setText(htmlContent, true);
        }catch (MessagingException e) {
            LOGGER.error("Error while sending status email to: " + user.getEmail());
        }

        LOGGER.info("Sending status email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateRequestConfirmation(User user, User user2, Trip confirmed) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("user2", user2);
        context.setVariable("request", confirmed);
        return templateEngine.process("requestconfirmation.html", context);
    }
    @Async
    @Override
    public void sendRequestEmail(User user,User user2,Trip request){
        String htmlContent = generateRequestConfirmation(user,user2,request);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing request email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Request confirmation");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending request email to: " + user.getEmail());
        }

        LOGGER.info("Sending request email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateSecureTokenEmail(User user, Integer tokenValue){
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("tokenValue", tokenValue);
        return templateEngine.process("securetoken.html", context);
    }
    @Async
    @Override
    public void sendSecureTokenEmail(User user, Integer tokenValue) {
        String htmlContent= generateSecureTokenEmail(user,tokenValue);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing secure token email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Verify Account");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending secure token email to: " + user.getEmail());
        }

        LOGGER.info("Sending secure token email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateProposal(User user, Proposal proposal) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("proposal", proposal);
        return templateEngine.process("proposal.html", context);
    }

    @Async
    @Override
    public void sendProposalEmail(User user,Proposal proposal) {
        String htmlContent = generateProposal(user,proposal);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing proposal email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Trip Proposal!");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending proposal email to: " + user.getEmail());
        }

        LOGGER.info("Sending proposal email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateProposalRequest(User user, Proposal proposal) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("proposal", proposal);
        return templateEngine.process("proposal.html", context);
    }

    @Async
    @Override
    public void sendProposalRequestEmail(User user, Proposal proposal){
        String htmlContent = generateProposalRequest(user,proposal);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing proposal request email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Trip Proposal!");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending proposal request email to: " + user.getEmail());
        }

        LOGGER.info("Sending proposal request email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateReset(User user, Integer hash) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("hash", hash);
        return templateEngine.process("resetpassword.html", context);
    }

    @Async
    @Override
    public void sendResetEmail(User user,Integer hash){
        String htmlContent = generateReset(user, hash);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            LOGGER.info("Preparing reset email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reset");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending reset email to: " + user.getEmail());
        }

        LOGGER.info("Sending reset email to: " + user.getEmail());
        mailSender.send(message);
    }


}
