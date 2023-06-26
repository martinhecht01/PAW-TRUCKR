package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;

import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;

import ar.edu.itba.paw.webapp.exceptions.ResetErrorException;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.VerifyAccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService us;
    private final ImageService is;
    private final TripServiceV2 ts;
    private final ReviewService revs;

    @Autowired
    public UserController(final UserService us, ImageService is, ReviewService revs, TripServiceV2 ts){
        this.us = us;
        this.is = is;
        this.revs = revs;
        this.ts = ts;
    }


    @RequestMapping("/")
    public ModelAndView landing() {
        LOGGER.info("Accessing landing page");
        return new ModelAndView("landing/index");
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) final String error, @RequestParam(value = "successVerification", required = false) final Boolean success) {
        LOGGER.info("Accessing login page");
        ModelAndView mav = new ModelAndView("landing/login");

        if(error != null && (Objects.equals(error, "InvalidCredentials") || Objects.equals(error, "UserNotVerified"))){
            mav.addObject("errorCode", error);
            mav.addObject("error", true);
        } else{
            mav.addObject("error", false);
        }
        mav.addObject("successVerification", success != null && success);
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam(value = "success", required = false) boolean success, @RequestParam(value = "email", required = false) String email, @ModelAttribute("userForm") final UserForm form) {
        LOGGER.info("Accessing register page");

        ModelAndView mav = new ModelAndView("landing/register");
        mav.addObject("success", success);
        mav.addObject("email", email == null ? "" : email);
        return mav;
    }

    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.info("Error in register form");
            return register(false, "", form);
        }

        User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword(),LocaleContextHolder.getLocale());
        if(user == null){
            LOGGER.info("User with CUIT {} already exists", form.getCuit());
            errors.rejectValue("cuit", "alreadyExists");
            return register(false, "", form);
        }
        LOGGER.info("User created with CUIT {} ", form.getCuit());
        return new ModelAndView("redirect:/register?success=true&email=" + user.getEmail());
    }


    @RequestMapping("/profile")
    public ModelAndView profile(@RequestParam(required = false) Integer id) {
        LOGGER.info("Accessing profile page");
        final ModelAndView mav = new ModelAndView("user/profile");

        if (id == null){
            mav.addObject("userRating", revs.getUserRating(getCurrentUser().getUserId()));
            mav.addObject("userReviews", revs.getUserReviews(getCurrentUser().getUserId()));
            mav.addObject("completedTrips", ts.getCompletedTripsCount(getCurrentUser().getUserId()));
            mav.addObject("currUser", getCurrentUser());
        }
        else{
            User currUser = us.getUserById(id).orElseThrow(UserNotFoundException::new);
            mav.addObject("userRating", revs.getUserRating(currUser.getUserId()));
            mav.addObject("userReviews", revs.getUserReviews(currUser.getUserId()));
            mav.addObject("completedTrips", ts.getCompletedTripsCount(currUser.getUserId()));
            System.out.println("COMPLETED TRIPS = " + ts.getCompletedTripsCount(currUser.getUserId()));
            mav.addObject("currUser", currUser);
        }
        return mav;
    }

    @RequestMapping("/myItinerary")
    public ModelAndView myIntinerary(@RequestParam(required = false) Integer ongoingPage, @RequestParam(required = false) Integer futurePage) {
        LOGGER.info("Accessing intinerary page");
        final ModelAndView mav = new ModelAndView("user/myItinerary");

        Integer maxOngoingPage = ts.getTotalPagesAllOngoingTrips(getCurrentUser().getUserId());
        Integer maxFuturePage = ts.getTotalPagesAllFutureTrips(getCurrentUser().getUserId());

        mav.addObject("maxOngoingPage", maxOngoingPage);
        mav.addObject("currentOngoingPage", ongoingPage == null || ongoingPage > maxOngoingPage ? 1 : ongoingPage);

        mav.addObject("maxFuturePage", maxFuturePage);
        mav.addObject("currentFuturePage", futurePage == null || futurePage > maxFuturePage ? 1 : futurePage);
//        mav.addObject("currentUser", getCurrentUser());
        mav.addObject("ongoingTrips", ts.getAllOngoingTrips(getCurrentUser().getUserId(), ongoingPage == null || ongoingPage > maxOngoingPage ? 1 : ongoingPage));
        mav.addObject("futureTrips", ts.getAllFutureTrips(getCurrentUser().getUserId(), futurePage == null || futurePage > maxFuturePage ? 1 : futurePage));
        return mav;
    }

    @RequestMapping("/pastTrips")
    public ModelAndView pastTrips() {
        LOGGER.info("Accessing past trips page");
        final ModelAndView mav = new ModelAndView("user/pastTrips");
        mav.addObject("currentUser", getCurrentUser());
        mav.addObject("pastTrips", ts.getAllPastTrips(getCurrentUser().getUserId()));
        return mav;
    }

    @RequestMapping("/myOffers")
    public ModelAndView myOffers() {
        LOGGER.info("Accessing myOffers page");
        final ModelAndView mav = new ModelAndView("user/myOffers");
        mav.addObject("offers", getCurrentUser().getProposals());
        return mav;
    }

    @RequestMapping(value = "/user/cancelOffer", method = RequestMethod.POST)
    public ModelAndView cancelOffer(@ModelAttribute("offerId") final int offerId) {
        LOGGER.info("Cancelling offer with id {}", offerId);
        ts.deleteOffer(offerId);
        return new ModelAndView("redirect:/myOffers");
    }

    @RequestMapping(value = "/verifyAccount", method = RequestMethod.GET)
    public ModelAndView verifyAccountView(@ModelAttribute("verifyAccountForm") final VerifyAccountForm form, @RequestParam(required = false) String token){

        if(token != null){
            form.setToken(token);
            return verifyAccount(form, validateForm(form));
        }

        LOGGER.info("Accessing verify account page");
        return new ModelAndView("user/verifyAccount");
    }

    private BindingResult validateForm(@Valid VerifyAccountForm form){
        return new BeanPropertyBindingResult(form, "verifyAccountForm");
    }

    @RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
    public ModelAndView verifyAccount(@Valid @ModelAttribute("verifyAccountForm") final VerifyAccountForm form,final BindingResult errors){

        if (errors.hasErrors()) {
            LOGGER.info("Error in verify account form");
            return verifyAccountView(form, null);
        }

        boolean success = us.verifyAccount(Integer.parseInt(form.getToken()), LocaleContextHolder.getLocale());

        if(!success) {
            LOGGER.info("Incorrect token");
            errors.rejectValue("token", "IncorrectToken");
            return verifyAccountView(form, null);
        }

        LOGGER.info("Account verified for token {}", form.getToken());
        return new ModelAndView("redirect:/login?successVerification=" + true);
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
        return new ModelAndView("user/resetPasswordSuccess");
    }

    @RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView resetPasswordRequest(@RequestParam(value = "cuit", required = false) String cuit){
        User user;
        user = us.getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        us.createReset(user.getUserId(),LocaleContextHolder.getLocale());
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

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public ModelAndView editUserView(@ModelAttribute("editUserForm") final EditUserForm form){
        return new ModelAndView("user/editProfile");
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute("editUserForm") final EditUserForm form,final BindingResult errors){
        if (errors.hasErrors()) {
            return editUserView(form);
        }
        if(!form.getProfileImage().isEmpty()) {
            int imgId = is.uploadImage(form.getProfileImage().getBytes());
            us.updateProfilePicture(getCurrentUser().getUserId(), imgId);
        }
        us.updateProfileName(getCurrentUser().getUserId(), form.getName());


        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping( value = "/user/{userId}/profilePicture", method = {RequestMethod.GET},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profilePicture(@PathVariable(value = "userId") int userId){
        return us.getProfilePicture(userId);
    }


}