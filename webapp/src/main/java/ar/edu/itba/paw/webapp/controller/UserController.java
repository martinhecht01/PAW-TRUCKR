package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.ResetErrorException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.VerifyErrorException;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.TripNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.VerifyAccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    private final UserService us;
    private final ImageService is;

    @Autowired
    public UserController(final UserService us, ImageService is){
        this.us = us;
        this.is = is;
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

        try{
            us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword());
        } catch (UserExistsException e){
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

        try{
            us.verifyAccount(Integer.parseInt(form.getToken()));
        } catch (VerifyErrorException e){
            errors.rejectValue("token", "incorrect");
            return verifyAccountView(form);
        }
        return new ModelAndView("redirect:/login");
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

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public ModelAndView editUserView(@ModelAttribute("editUserForm") final EditUserForm form){
        return new ModelAndView("user/editProfile");
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute("editUserForm") final EditUserForm form,final BindingResult errors){
        if (errors.hasErrors()) {
            return editUserView(form);
        }

        int imgId = is.uploadImage(form.getProfileImage().getBytes());

        us.updateProfilePicture(getCurrentUser().getUserId(), imgId);
        us.updateProfileName(getCurrentUser().getUserId(), form.getName());


        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping( value = "/user/{userId}/profilePicture", method = {RequestMethod.GET},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profilePicture(@PathVariable(value = "userId") int userId) throws IOException {
        return us.getProfilePicture(userId);
    }


}