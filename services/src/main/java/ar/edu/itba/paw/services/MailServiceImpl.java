package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Service
public class MailServiceImpl implements MailService {


    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    private String getHtmlBody(String template, Map<String, Object> variables) {
        Context thymeleafContext = new Context(LocaleContextHolder.getLocale());
        if(variables == null) {
            variables = new HashMap<>();
        }
        return templateEngine.process(template, thymeleafContext);
    }


    private String generateEmailConfirmation(User confirmed, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        System.out.println(locale.toString());
        context.setVariable("user", confirmed);
        return templateEngine.process("emailconfirmation.html", context);
    }
    @Async
    @Override
    public void sendConfirmationEmail(User user, Locale locale){
        String htmlContent = generateEmailConfirmation(user,locale);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            LOGGER.info("Preparing confirmation email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("AccountConfirmation", null, locale);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending confirmation email to: " + user.getEmail());
        }

        LOGGER.info("Sending confirmation email to: " + user.getEmail());
        mailSender.send(message);
    }
    private String generateTripConfirmation(User user,User user2, Trip confirmed, Locale locale) {
        Context context = new Context();

        context.setVariable("user", user);
        context.setVariable("user2", user2);
        context.setVariable("trip", confirmed);
        context.setLocale(locale);
        return templateEngine.process("tripconfirmation.html", context);
    }

    @Async
    @Override
    public void sendTripEmail(User user,User user2,Trip trip, Locale locale){
        String htmlContent = generateTripConfirmation(user,user2,trip,locale);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            LOGGER.info("Preparing trip email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("TripConfirmation", null, locale);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        }catch (MessagingException e) {
            LOGGER.error("Error while sending trip email to: " + user.getEmail());
        }

        mailSender.send(message);
    }
    private String generateTripCompletion(User user, Trip completed,Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("user", user);

        context.setVariable("trip", completed);
        return templateEngine.process("tripcomplete.html", context);
    }

    @Override
    @Async
    public void sendCompletionEmail(User user, Trip trip,Locale locale){
        String htmlContent = generateTripCompletion(user,trip,locale);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("TripCompleted", null, locale);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        }catch (MessagingException e) {
            LOGGER.error("Error while sending completion email to: " + user.getEmail());
        }
        LOGGER.info("Sending completion email to: " + user.getEmail());
        mailSender.send(message);
    }
    private String generateTripStatus(User user, Trip completed,Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("user", user);

        context.setVariable("trip", completed);
        return templateEngine.process("statusupdate.html", context);
    }
    @Override
    @Async
    public void sendStatusEmail(User user, Trip trip,Locale locale){
        String htmlContent = generateTripStatus(user,trip, locale);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("StatusUpdate", null, locale);
            helper.setSubject(subject);
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

    private String generateSecureTokenEmail(User user, Integer tokenValue, Locale locale){
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("user", user);
        context.setVariable("tokenValue", tokenValue);
        return templateEngine.process("securetoken.html", context);
    }
    @Async
    @Override
    public void sendSecureTokenEmail(User user, Integer tokenValue, Locale locale) {
        String htmlContent= generateSecureTokenEmail(user,tokenValue, locale);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing secure token email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("VerifyTitle", null, locale);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending secure token email to: " + user.getEmail());
        }

        LOGGER.info("Sending secure token email to: " + user.getEmail());
        mailSender.send(message);
    }

    private String generateProposal(User user, Proposal proposal, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("user", user);
        context.setVariable("proposal", proposal);
        return templateEngine.process("proposal.html", context);
    }

    @Async
    @Override
    public void sendProposalEmail(User user,Proposal proposal, Locale locale) {
        String htmlContent = generateProposal(user,proposal, locale);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            LOGGER.info("Preparing proposal email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject = messageSource.getMessage("TripProposal", null, locale);
            helper.setSubject(subject);
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

    private String generateReset(User user, Integer hash, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("user", user);
        context.setVariable("hash", hash);
        return templateEngine.process("resetpassword.html", context);
    }

    @Async
    @Override
    public void sendResetEmail(User user,Integer hash, Locale locale){
        String htmlContent = generateReset(user, hash, locale);
        MimeMessage message = mailSender.createMimeMessage();

        try{
            LOGGER.info("Preparing reset email");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            String subject =messageSource.getMessage("ResetPassword", null, locale);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending reset email to: " + user.getEmail());
        }

        LOGGER.info("Sending reset email to: " + user.getEmail());
        mailSender.send(message);
    }


}
