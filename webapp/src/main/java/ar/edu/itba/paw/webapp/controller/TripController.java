package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Trip;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TripController {

    @RequestMapping("/trip")
    public ModelAndView register() {
        return new ModelAndView("landing/truckDetails");
    }

    @RequestMapping("/browseTrips")
    public ModelAndView browseTrips() {
        final ModelAndView view = new ModelAndView("landing/browseTrips");
        List<Trip> trips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            trips.add(new Trip("ABC123", 4000,5, new Date("12/1/2023"), new Date("02/12/2023"), "Bsas", "Cor" ));
        }
        view.addObject("offers", trips);
        return  view;
    }

}
