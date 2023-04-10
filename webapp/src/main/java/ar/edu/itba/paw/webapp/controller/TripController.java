package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.form.TripForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TripController {

    private final TripService ts;
    private final UserService us;

    @Autowired
    public TripController(final TripService ts, final UserService us){
        this.ts = ts;
        this.us = us;
    }

    @RequestMapping("/trip")
    public ModelAndView register() {
        return new ModelAndView("landing/truckDetails");
    }

    @RequestMapping("/browseTrips")
    public ModelAndView browseTrips() {
        final ModelAndView view = new ModelAndView("landing/browseTrips");
        List<Trip> trips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            trips.add(new Trip(1,"111", "ABC123", 4000,5, new Date("12/1/2023"), new Date("02/12/2023"), "Bsas", "Cor", "Refrigerado"));
        }
        view.addObject("offers", trips);
        return  view;
    }

    @RequestMapping("/createTrip")
    public ModelAndView createTrip(@ModelAttribute("tripForm") final TripForm form) {
        final ModelAndView view = new ModelAndView("landing/createTrip");
        return view;
    }


    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tripForm") final TripForm form, final BindingResult errors) {
        System.out.println("Apretaste bien");
        System.out.println(errors.toString());
        if (errors.hasErrors()) {
            System.out.println(form.getDepartureDate());
            return createTrip(form);
        }

        //1 crear usuario
        System.out.println(form);
        us.createUser(form.getEmail(), form.getName(), form.getId());
        Date departure = java.sql.Date.valueOf(form.getDepartureDate());
        Date arrival = java.sql.Date.valueOf(form.getArrivalDate());
        //2 crear viaje
        ts.createTrip(form.getId(), form.getLicensePlate(), form.getAvailableWeight(), form.getAvailableVolume(), departure, arrival, form.getOrigin(), form.getDestination(), form.getCargoType());
        //que pasa si un usuario arma 2 trips
        //get User -> si no existe creo | get userId
            //Error duplicate key.
        //final Trip trip = ts.createTrip( "mdithurbide@itba.edu.ar", "Manuel Dithurbide", "20-43988795-9");
        return new ModelAndView("redirect:/browseTrips");
    }


}
