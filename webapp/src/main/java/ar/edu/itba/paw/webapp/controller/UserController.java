package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.ResetErrorException;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.TripNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
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
    public ModelAndView login(){
        return new ModelAndView("landing/login");
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
            if(us.existsUser(form.getCuit())){
                errors.rejectValue("cuit", "alreadyExists");
            }
            return register(form);
        }

        if(us.existsUser(form.getCuit())){
            errors.rejectValue("cuit", "alreadyExists");
            return register(form);
        }

        us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword());
        return new ModelAndView("redirect:/browseTrips");
    }


    @RequestMapping("/profile")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("user/profile");

       // mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));

        return mav;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@ModelAttribute("userForm") final ResetPasswordForm form, @RequestParam(value = "hash") Integer hash){
        Reset reset;
        try{
            reset = us.getResetByHash(hash).orElseThrow(TripNotFoundException::new);
        } catch (ResetErrorException e){
            ModelAndView mv = new ModelAndView("landing/error");
            mv.addObject("errorMsgCode", e.getMessage());
            mv.setViewName("landing/error");
            return mv;
        }
        if(reset.isCompleted()){
            return new ModelAndView("redirect:/login");
        }
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
        try {
            user = us.getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        }catch (UserNotFoundException e){
            return resetPasswordRequest("true", cuit, "false");
        }
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
            return us.getUserByCuit(userDetails1.getUsername()).get();
        }
        return null;
    }

}