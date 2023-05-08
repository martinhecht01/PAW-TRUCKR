package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.TripNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.TripForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class TripController {

    private final TripService ts;
    private final UserService us;
    private final CityService cs;

    @Autowired
    public TripController(final TripService ts, final UserService us, final CityService cs){
        this.ts = ts;
        this.us = us;
        this.cs = cs;
    }

    @RequestMapping("/trips/browse")
    public ModelAndView browse(@RequestParam(defaultValue = "1") String page,
                                    @RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String destination,
                                    @RequestParam(required = false) Integer minAvailableVolume,
                                    @RequestParam(required = false) Integer minAvailableWeight,
                                    @RequestParam(required = false) Integer minPrice,
                                    @RequestParam(required = false) Integer maxPrice,
                                    @RequestParam(required = false) String sortOrder,
                                    @RequestParam(required = false) String departureDate,
                                    @RequestParam(required = false) String arrivalDate)
    {
        Integer maxPages = ts.getTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
        Integer currPage = Integer.parseInt(page);
        if(Integer.parseInt(page) < 1 || Integer.parseInt(page) > maxPages ){
            page = "1";
        }

        final ModelAndView view = new ModelAndView("trips/browse");

        view.addObject("maxPage", maxPages);
        view.addObject("currentPage", page);
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("minPrice",minPrice);
        view.addObject("maxPrice",maxPrice);
        view.addObject("sortOrder",sortOrder);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate",arrivalDate);
        List<Trip> trips = ts.getAllActiveTrips(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, Integer.parseInt(page));
        System.out.println("TRIPS SIZE"+trips.size());
        view.addObject("offers", trips);
        return view;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/trips/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tripForm") final TripForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return createTrip(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        User user = getUser();

        Trip trip = ts.createTrip(
                user.getCuit(),
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
        return new ModelAndView("redirect:/trips/success?id="+trip.getTripId());
    }


    @RequestMapping(value = "/trips/create", method = { RequestMethod.GET })
    public ModelAndView createTrip(@ModelAttribute("tripForm") final TripForm form) {
        return new ModelAndView("trips/create");
    }

    @RequestMapping("/trips/details")
    public ModelAndView tripDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm form) {
        final ModelAndView mav = new ModelAndView("trips/details");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        mav.addObject("userId", getUser().getUserId());
        mav.addObject("user", us.getUserById(trip.getUserId()).orElseThrow(UserNotFoundException :: new));
        return mav;
    }


    @RequestMapping(value = "/trips/sendProposal", method = { RequestMethod.POST })
    public ModelAndView accept(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) throws MessagingException {
        if (errors.hasErrors()) {
            return tripDetail(id, form);
        }

        User user = getUser();

        try {
            ts.sendProposal(id, user.getUserId(), form.getDescription());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        ModelAndView mav = new ModelAndView("redirect:/trips/reserveSuccess");

        mav.addObject("id",id);
        return mav;
    }

    @RequestMapping(value = "/trips/acceptProposal", method = { RequestMethod.POST })
    public ModelAndView acceptProposal(@RequestParam("id") int id) {
        System.out.println("accepting proposal ID = " + id);
        ts.acceptTrip(id);
        ModelAndView mav = new ModelAndView("trips/acceptSuccess");

        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }


    @RequestMapping("/trips/success")
    public ModelAndView tripDetail(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("trips/success");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/reserveSuccess")
    public ModelAndView tripReserveSuccess(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("trips/reserveSuccess");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/myTrips")
    public ModelAndView myTrips(){
        User user = getUser();
        final ModelAndView mav = new ModelAndView("trips/myTrips");
        mav.addObject("acceptedTrips",ts.getAllAcceptedTripsByUserId(user.getUserId()));
        mav.addObject("myTrips", ts.getAllActiveTripsAndProposalCount(user.getUserId()));
        return mav;
    }

    @RequestMapping("/trips/manageTrip")
    public ModelAndView manageTrip(@RequestParam("tripId") int tripId) {
        final ModelAndView mav = new ModelAndView("trips/manageTrip");
        int userId = getUser().getUserId();
        Trip trip = ts.getTripByIdAndUserId(tripId, userId).orElseThrow(TripNotFoundException::new);
        if(trip.getAcceptUserId() > 0)
            mav.addObject("acceptUser", us.getUserById(trip.getAcceptUserId()).orElseThrow(UserNotFoundException::new));
        mav.addObject("trip", trip);
        mav.addObject("userId", userId);
        mav.addObject("offers", ts.getProposalsForTripId(trip.getTripId()));
        return mav;
    }

    @RequestMapping(value = "/trips/confirmTrip", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("id") int tripId) {
        User user = getUser();
        ts.confirmTrip(tripId, user.getUserId());
        if (Objects.equals(user.getRole(), "TRUCKER"))
            return new ModelAndView("redirect:/trips/manageTrip?tripId="+tripId);
        else
            return new ModelAndView("redirect:/trips/details?id="+tripId);
    }

    private User getUser() {
        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
    }

}
