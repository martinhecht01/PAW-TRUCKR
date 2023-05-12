package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.webapp.exceptions.ResetErrorException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.VerifyAccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService us;

    @Autowired
    public UserController(final UserService us){
        this.us = us;
    }


    @RequestMapping("/")
    public ModelAndView landing() {
        LOGGER.info("Accessing landing page");
        return new ModelAndView("landing/index");
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) final String error){
        LOGGER.info("Accessing login page");
        ModelAndView mav = new ModelAndView("landing/login");
        mav.addObject("error", error != null);
        return mav;
    }

// TODO revisar este metodo

//    @RequestMapping(value = "/createUser", method = { RequestMethod.GET })
//    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form) {
//        return new ModelAndView("redirect:/explore");
//    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("userForm") final UserForm form) {
        LOGGER.info("Accessing register page");
        return new ModelAndView("landing/register");
    }

    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.info("Error in register form");
            return register(form);
        }

        User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword());
        if(user == null){
            LOGGER.info("User with CUIT {} already exists", form.getCuit());
            errors.rejectValue("cuit", "alreadyExists");
            return register(form);
        }
        LOGGER.info("User created with CUIT {} ", form.getCuit());
        return new ModelAndView("redirect:/login");
    }


    @RequestMapping("/profile")
    public ModelAndView profile() {
        LOGGER.info("Accessing profile page");
        final ModelAndView mav = new ModelAndView("user/profile");

       // mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));

        return mav;
    }
    @RequestMapping(value = "/verifyAccount", method = RequestMethod.GET)
    public ModelAndView verifyAccountView(@ModelAttribute("verifyAccountForm") final VerifyAccountForm form){
        LOGGER.info("Accessing verify account page");
        return new ModelAndView("user/verifyAccount");
    }

    @RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
    public ModelAndView verifyAccount(@Valid @ModelAttribute("verifyAccountForm") final VerifyAccountForm form,final BindingResult errors){
        if (errors.hasErrors()) {
            LOGGER.info("Error in verify account form");
            return verifyAccountView(form);
        }

        boolean success = us.verifyAccount(Integer.parseInt(form.getToken()));
        if(!success){
            LOGGER.info("Incorrect token");
            errors.rejectValue("token", "incorrect");
            return verifyAccountView(form);
        }
        LOGGER.info("Account verified for token {}", form.getToken());
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@ModelAttribute("userForm") final ResetPasswordForm form, @RequestParam(value = "hash") Integer hash){
        us.getResetByHash(hash).orElseThrow(ResetErrorException::new);
        LOGGER.info("Accessing reset password page");
        ModelAndView mv = new ModelAndView("user/resetPassword");
        mv.addObject("hash", hash);
        return mv;
    }



    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(@RequestParam("hash") Integer hash, @Valid @ModelAttribute("userForm") final ResetPasswordForm form, final BindingResult errors){
        if(errors.hasErrors()){
            LOGGER.info("Error in reset password form");
            return resetPassword(form, hash);
        }
        us.resetPassword(hash, form.getPassword());
        LOGGER.info("Password reset");
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView resetPasswordRequest(@RequestParam(value = "cuit", required = false) String cuit){
        User user;
        user = us.getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        us.createReset(user.getUserId());
        LOGGER.info("Reset password request sent for user with CUIT {}", cuit);
        return resetPasswordRequest("false", user.getEmail(), "true");
    }

    @RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.GET)
    public ModelAndView resetPasswordRequest(@RequestParam(value = "error", required = false) String error,@RequestParam(value = "email", required = false) String email, @RequestParam(value = "emailSent", required = false) String emailSent){
        LOGGER.info("Accessing reset password request page");
        ModelAndView mv = new ModelAndView("user/sendResetRequest");
        mv.addObject("email", email);
        mv.addObject("emailSent", Boolean.parseBoolean(emailSent));
        mv.addObject("error", Boolean.parseBoolean(error));
        return mv;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).orElseThrow(UserNotFoundException::new);
        }
        return null;
    }

}