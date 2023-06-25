package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.form.AlertForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AlertsController {

    private final AlertService as;

    private final UserService us;

    private final CityService cs;

    @Autowired
    public AlertsController(final AlertService as, final UserService us, final CityService cs) {
        this.as = as;
        this.us = us;
        this.cs = cs;
    }

    @RequestMapping(value = "/alerts/create", method = RequestMethod.POST)
    public ModelAndView createAlert(@Valid @ModelAttribute("alertForm") final AlertForm form, final BindingResult errors) {
        if(errors.hasErrors())
            return new ModelAndView("alerts/createAlert");

        as.createAlert(getCurrentUser(), form.getOrigin(), form.getMaxWeight(), form.getMaxWeight(), form.getFromDate() != null ? LocalDateTime.parse(form.getFromDate()) : null, form.getToDate() != null ? LocalDateTime.parse(form.getToDate()) : null);
        return new ModelAndView("redirect:/alerts/myAlerts");
    }

    @RequestMapping(value = "/alerts/createAlert", method = RequestMethod.GET)
    public ModelAndView createAlertForm(@ModelAttribute("alertForm") final AlertForm form) {
        return new ModelAndView("alerts/createAlert");
    }

//    @RequestMapping(value = "/alerts/editAlert", method = RequestMethod.POST)
//    public ModelAndView editAlert(@Valid @ModelAttribute("alertForm") final AlertForm form, final BindingResult errors) {
//
//    }

//    @RequestMapping(value = "/alerts/editAlert", method = RequestMethod.GET)
//    public ModelAndView editAlertForm() {
//
//    }

    @RequestMapping("/alerts/myAlerts")
    public ModelAndView myAlerts() {
        ModelAndView mav = new ModelAndView("alerts/myAlerts");
        mav.addObject("currentUser", getCurrentUser());
        return mav;
    }

    @RequestMapping(value = "/alerts/delete", method = RequestMethod.POST)
    public ModelAndView deleteAlert() {
        as.deleteAlert(getCurrentUser());
        return new ModelAndView("redirect:/alerts/myAlerts");
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof AuthUserDetailsImpl){
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).orElseThrow(UserNotFoundException::new);
        }
        return null;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }

}
