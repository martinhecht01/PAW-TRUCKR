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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    @RequestMapping("/browseTrips")
    public ModelAndView browseTrips(@RequestParam(defaultValue = "1") String page,
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

        final ModelAndView view = new ModelAndView("landing/browseTrips");

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
        view.addObject("offers", trips);
        return view;
    }

    @ModelAttribute("currentRole")
    public static String getCurrentRole() {
        Collection<? extends GrantedAuthority> c = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (c.contains(new SimpleGrantedAuthority("ROLE_TRUCKER"))){
            return "TRUCKER";
        }
        else if (c.contains(new SimpleGrantedAuthority("ROLE_PROVIDER"))){
            return "PROVIDER";
        }
        return "";


    }

    @RequestMapping("/explore")
    public ModelAndView creates() {
        String role = getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/browseRequests");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/browseTrips");
        }
        return new ModelAndView("redirect:/");
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/create/trip", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tripForm") final TripForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return createTrip(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

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
        ModelAndView view = new ModelAndView("redirect:/trips/success?id="+trip.getTripId());
        return view;
    }

    @RequestMapping(value = "/create/trip", method = { RequestMethod.GET })
    public ModelAndView createTrip(@ModelAttribute("tripForm") final TripForm form) {
        final ModelAndView view = new ModelAndView("landing/createTrip");
        return view;
    }

    @RequestMapping("/create")
    public ModelAndView create(){
        String role = getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/create/trip");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/create/request");
        }
        return new ModelAndView("redirect:/");
    }


    @RequestMapping("/tripDetail")
    public ModelAndView tripDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm form) {
        final ModelAndView mav = new ModelAndView("landing/tripDetails");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        mav.addObject("user", us.getUserById(trip.getUserId()));
        return mav;
    }

    @RequestMapping("/trips/manageTrip")
    public ModelAndView manageTrip(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("landing/manageTrip");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);

        mav.addObject("offers", ts.getProposalsForTripId(trip.getTripId()));
        return mav;
    }

    @RequestMapping(value = "/sendProposal", method = { RequestMethod.POST })
    public ModelAndView accept(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return tripDetail(id, form);
        }

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        ts.sendProposal(id, user.getUserId(), form.getDescription());

        return new ModelAndView("redirect:/browseTrips");
    }

    @RequestMapping(value = "/trips/acceptProposal", method = { RequestMethod.POST })
    public ModelAndView acceptProposal(@RequestParam("id") int id) {
        System.out.println("accepting proposal ID = " + id);
        ts.acceptTrip(id);
        return new ModelAndView("redirect:/browseTrips");
    }


    @RequestMapping("/trips/success")
    public ModelAndView tripDetail(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("landing/tripSuccess");
        Trip trip = ts.getTripById(id).orElseThrow(TripNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/myTrips")
    public ModelAndView myTrips(){
        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        final ModelAndView mav = new ModelAndView("landing/myTrips");
        mav.addObject("offers", ts.getAllActiveTripsByUserId(user.getUserId()));
        return mav;
    }


}
