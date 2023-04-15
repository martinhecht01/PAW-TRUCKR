package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.form.TripForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
        return new ModelAndView("tripDetails");
    }

    @RequestMapping("/browseTrips")
    public ModelAndView browseTrips(@RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String destination,
                                    @RequestParam(required = false) Integer minAvailableVolume,
                                    @RequestParam(required = false) Integer minAvailableWeight,
                                    @RequestParam(required = false) Integer minPrice,
                                    @RequestParam(required = false) Integer maxPrice,
                                    @RequestParam(required = false) String sortOrder,
                                    @RequestParam(required = false) String departureDate,
                                    @RequestParam(required = false) String arrivalDate)
    {
        final ModelAndView view = new ModelAndView("landing/browseTrips");
        List<Trip> trips = ts.getAllActiveTrips(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
        view.addObject("offers", trips);
        return view;
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

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        ts.createTrip(
                form.getEmail(),
                form.getName(),
                form.getId(),
                form.getLicensePlate(),
                form.getAvailableWeight(),
                form.getAvailableVolume(),
                departure,
                arrival,
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                form.getPrice()
        );

        return new ModelAndView("redirect:/browseTrips");
    }

    @RequestMapping("/tripdetail") // Antes aceptaba negativos, ahora no!
    public ModelAndView profile(@RequestParam("id") int id) {
        System.out.println(id);
        final ModelAndView mav = new ModelAndView("landing/tripDetails");
        Trip trip = ts.getTripById(id);
        mav.addObject("trip", trip);
        mav.addObject("user", us.getUserById(trip.getUserId()));
        return mav;
    }


}
