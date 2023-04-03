package ar.edu.itba.paw.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class TripController {

    @RequestMapping("/trip")
    public ModelAndView register() {
        return new ModelAndView("landing/truckDetails");
    }
}
