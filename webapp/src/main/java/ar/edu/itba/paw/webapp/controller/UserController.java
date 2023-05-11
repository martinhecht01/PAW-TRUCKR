package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.webapp.exceptions.ResetErrorException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.VerifyAccountForm;
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

    private final UserService us;

    @Autowired
    public UserController(final UserService us){
        this.us = us;
    }


    @RequestMapping("/")
    public ModelAndView landing() {
        return new ModelAndView("landing/index");
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) final String error){
        ModelAndView mav = new ModelAndView("landing/login");
        mav.addObject("error", error != null);
        return mav;
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.GET })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form) {
        return new ModelAndView("redirect:/browseTrips");
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("userForm") final UserForm form) {
        return new ModelAndView("landing/register");
    }

    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return register(form);
        }

        User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword());
        if(user == null){
            errors.rejectValue("cuit", "alreadyExists");
            return register(form);
        }
        return new ModelAndView("redirect:/browseTrips");
    }


    @RequestMapping("/profile")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("user/profile");

       // mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));

        return mav;
    }
    @RequestMapping(value = "/verifyAccount", method = RequestMethod.GET)
    public ModelAndView verifyAccountView(@ModelAttribute("verifyAccountForm") final VerifyAccountForm form){
        return new ModelAndView("user/verifyAccount");
    }

    @RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
    public ModelAndView verifyAccount(@Valid @ModelAttribute("verifyAccountForm") final VerifyAccountForm form,final BindingResult errors){
        if (errors.hasErrors()) {
            return verifyAccountView(form);
        }

        boolean success = us.verifyAccount(Integer.parseInt(form.getToken()));
        if(!success){
            errors.rejectValue("token", "incorrect");
            return verifyAccountView(form);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@ModelAttribute("userForm") final ResetPasswordForm form, @RequestParam(value = "hash") Integer hash){
        us.getResetByHash(hash).orElseThrow(ResetErrorException::new);
        ModelAndView mv = new ModelAndView("user/resetPassword");
        mv.addObject("hash", hash);
        return mv;
    }



    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(@RequestParam("hash") Integer hash, @Valid @ModelAttribute("userForm") final ResetPasswordForm form, final BindingResult errors){
        if(errors.hasErrors())
            return resetPassword(form, hash);
        us.resetPassword(hash, form.getPassword());
        return new ModelAndView("redirect:/login");
    }
    @RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView resetPasswordRequest(@RequestParam(value = "cuit", required = false) String cuit){
        User user;
        user = us.getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        us.createReset(user.getUserId());
        return resetPasswordRequest("false", user.getEmail(), "true");
    }

    @RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.GET)
    public ModelAndView resetPasswordRequest(@RequestParam(value = "error", required = false) String error,@RequestParam(value = "email", required = false) String email, @RequestParam(value = "emailSent", required = false) String emailSent){
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