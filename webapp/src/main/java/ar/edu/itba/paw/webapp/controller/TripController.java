package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exception.TripNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.TripForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(TripNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchTrip() {
        final ModelAndView view = new ModelAndView("landing/404");
        view.addObject("title", "Viaje");
        return view;
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
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("minPrice",minPrice);
        view.addObject("maxPrice",maxPrice);
        view.addObject("sortOrder",sortOrder);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate",arrivalDate);
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
        if (errors.hasErrors()) {
            return createTrip(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        ts.createTrip(
                form.getEmail(),
                form.getName(),
                form.getId(),
                form.getLicensePlate(),
                Integer.parseInt(form.getAvailableWeight()),
                Integer.parseInt(form.getAvailableVolume()),
                departure,
                arrival,
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                Integer.parseInt(form.getPrice())
        );

        return new ModelAndView("redirect:/browseTrips");
    }

    @RequestMapping("/tripDetail")
    public ModelAndView tripDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm form) {
        final ModelAndView mav = new ModelAndView("landing/tripDetails");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        mav.addObject("user", us.getUserById(trip.getUserId()));
        return mav;
    }

    @RequestMapping(value = "/accept", method = { RequestMethod.POST })
    public ModelAndView accept(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return tripDetail(id, form);
        }

        ts.acceptTrip(id, form.getEmail(),form.getName(),form.getCuit());

        return new ModelAndView("redirect:/browseTrips");
    }


}
