package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.*;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.SearchTripForm;
import ar.edu.itba.paw.webapp.form.TripForm;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class TripController {

    private final TripServiceV2 ts;
    private final UserService us;
    private final CityService cs;

    private final ImageService is;

    private final static Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final ReviewService revs;

    @Autowired
    public TripController(final TripServiceV2 ts, final UserService us, final CityService cs, ImageService is, ReviewService revs){
        this.ts = ts;
        this.us = us;
        this.cs = cs;
        this.is = is;
        this.revs = revs;
    }

    @RequestMapping("/trips/search")
    public ModelAndView searchGet(@ModelAttribute("searchTripForm") final SearchTripForm form){
        final ModelAndView mav = new ModelAndView("trips/searchTrip");
        return mav;
    }

    @RequestMapping(value = "/trips/search", method = RequestMethod.POST)
    public ModelAndView searchPost(@Valid @ModelAttribute("searchTripForm") final SearchTripForm form, final BindingResult result){
        if(result.hasErrors()){
            return searchGet(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        return new ModelAndView("redirect:/trips/search/results?" +
                "origin="
                +form.getOrigin()
                +"&destination=" + form.getDestination()
                +"&minAvailableVolume=" + form.getAvailableVolume()
                +"&minAvailableWeight=" + form.getAvailableWeight()
                +"&departureDate=" + departure
                +"&arrivalDate=" + arrival
                +"&type=" + form.getCargoType()
        );
    }

    @RequestMapping("/trips/search/results")
    public ModelAndView searchResults(@RequestParam(defaultValue = "1") String page,
                                      @RequestParam(required = false) String origin,
                                      @RequestParam(required = false) String destination,
                                      @RequestParam(required = false) Integer minAvailableVolume,
                                      @RequestParam(required = false) Integer minAvailableWeight,
                                      @RequestParam(required = false) Integer minPrice,
                                      @RequestParam(required = false) Integer maxPrice,
                                      @RequestParam(required = false) String sortOrder,
                                      @RequestParam(required = false) String departureDate,
                                      @RequestParam(required = false) String arrivalDate,
                                      @RequestParam(required = false) String type){
        LOGGER.info("Accessing search results trips page");
        Integer maxPages = ts.getActiveTripsTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, null, null, type);
        Integer currPage = Integer.parseInt(page);

        if(currPage < 1 || currPage > maxPages ){
            currPage = 1;
        }

        final ModelAndView view = new ModelAndView("trips/results");

        view.addObject("maxPage", maxPages);
        view.addObject("currentPage", currPage);
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("minPrice",minPrice);
        view.addObject("maxPrice",maxPrice);
        view.addObject("sortOrder",sortOrder);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate", arrivalDate);


        //TODO: Agregar las fechas aca
        List<Trip> trips = ts.getAllActiveTrips(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, null, null, type, Integer.parseInt(page));
        LOGGER.debug("TRIPS SIZE = {}",trips.size());
        view.addObject("offers", trips);
        return view;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/trips/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tripForm") final TripForm form, final BindingResult errors) {
        if (errors.hasErrors() || Objects.requireNonNull(getUser()).getUserId() == null) {
            LOGGER.info("Error creating trip");
            return createTrip(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());

        User user = getUser();

        Trip trip = ts.createTrip(
                user.getUserId(),
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
        int imageid=is.uploadImage(form.getTripImage().getBytes());
        ts.updateTripPicture(trip.getTripId(),imageid);
        LOGGER.info("Trip created successfully");
        return new ModelAndView("redirect:/trips/success?id="+trip.getTripId());
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
                               @RequestParam(required = false) String arrivalDate,
                               @RequestParam(required = false) String type) {
        LOGGER.info("Accessing browse trips page");
        Integer maxPages = ts.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
        Integer currPage = Integer.parseInt(page);
        if (currPage < 1 || currPage > maxPages) {
            page = "1";
        }

        final ModelAndView view = new ModelAndView("trips/browse");

        view.addObject("maxPage", maxPages);
        view.addObject("currentPage", currPage);
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("minPrice",minPrice);
        view.addObject("maxPrice",maxPrice);
        view.addObject("sortOrder",sortOrder);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate", arrivalDate);
        view.addObject("offers", ts.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, Integer.parseInt(page)));
        return view;
    }


    @RequestMapping(value = "/trips/create", method = { RequestMethod.GET })
    public ModelAndView createTrip(@ModelAttribute("tripForm") final TripForm form) {
        LOGGER.info("Accessing create trip page");
        ModelAndView view = new ModelAndView("trips/create");
        return view;
    }

    @RequestMapping("/trips/details")
    public ModelAndView tripDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm form) {
        LOGGER.info("Accessing trip details page with trip Id: {}", id);
        final ModelAndView mav = new ModelAndView("trips/details");
        Trip trip = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }


    @RequestMapping(value = "/trips/sendProposal", method = { RequestMethod.POST })
    public ModelAndView sendProposal(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) throws MessagingException {
        if (errors.hasErrors()) {
            LOGGER.info("Error sending proposal");
            return tripDetail(id, form);
        }

        User user = getUser();

        if(user == null)
            throw new UserNotFoundException();

        ts.createProposal(id, user.getUserId(), form.getDescription(), form.getPrice());
        LOGGER.info("Proposal with Id: {} sent successfully", id);
        ModelAndView mav = new ModelAndView("redirect:/trips/reserveSuccess");
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping(value="/trips/sendReview", method = { RequestMethod.POST })
    public ModelAndView sendReview(@RequestParam("tripid") int tripid, @RequestParam("userid") int userid, @RequestParam ("rating") int rating, @RequestParam("description") String comment){
        User user = getUser();
        if (user == null){
            return new ModelAndView("redirect:/login");
        }
        revs.createReview(tripid, userid, rating, comment);
        if (Objects.equals(user.getRole(), "TRUCKER"))
            return new ModelAndView("redirect:/trips/manageTrip?tripId="+ tripid);
        else
            return new ModelAndView("redirect:/trips/details?id="+ tripid);
    }
    @RequestMapping("/trips/acceptSuccess")
    public ModelAndView acceptSuccess(@RequestParam("tripId") String tripId){
        ModelAndView mav = new ModelAndView("trips/acceptSuccess");
        Trip trip = ts.getTripOrRequestByIdAndUserId(Integer.parseInt(tripId), getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/success")
    public ModelAndView tripDetail(@RequestParam("id") int id) {
        LOGGER.info("Accessing trip success page with trip Id: {}", id);
        final ModelAndView mav = new ModelAndView("trips/success");
        Trip trip = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/reserveSuccess")
    public ModelAndView tripReserveSuccess(@RequestParam("id") int id) {
        LOGGER.info("Accessing trip reserve success page with trip Id: {}", id);
        final ModelAndView mav = new ModelAndView("trips/reserveSuccess");
        Trip trip = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("trip", trip);
        return mav;
    }

    @RequestMapping("/trips/myTrips")
    public ModelAndView myTrips(@RequestParam(value = "acceptPage", required = false, defaultValue = "1") final Integer acceptPage, @RequestParam(value = "activePage", required = false, defaultValue = "1") final Integer activePage){
        User user = getUser();
        LOGGER.info("User with id: {} accessing my trips page", user.getUserId());

        Integer maxActivePage = ts.getTotalPagesActivePublications(user);
        Integer maxAcceptPage = ts.getTotalPagesExpiredPublications(user);

        if(activePage > maxActivePage || activePage < 1){
            maxActivePage = 1;
        }

        if(acceptPage > maxAcceptPage || acceptPage < 1){
            maxAcceptPage = 1;
        }

        final ModelAndView mav = new ModelAndView("trips/myTrips");
        mav.addObject("currentPageActive", activePage);
        mav.addObject("maxActivePage", maxActivePage);

        mav.addObject("currentPageAccepted", acceptPage);
        mav.addObject("maxAcceptedPage", maxAcceptPage);
        mav.addObject("expiredPublications",ts.getAllExpiredPublications(user.getUserId(), acceptPage));
        mav.addObject("activePublications", ts.getAllActivePublications(user.getUserId(), activePage));
        return mav;
    }

    @RequestMapping("/trips/manageTrip")
    public ModelAndView manageTrip(@RequestParam("tripId") int tripId, @ModelAttribute("acceptForm") final AcceptForm form ) {
        LOGGER.info("Accessing manage trip page with trip Id: {}", tripId);
        final ModelAndView mav = new ModelAndView("trips/manageTrip");
        Trip trip = ts.getTripOrRequestByIdAndUserId(tripId, getUser()).orElseThrow(TripOrRequestNotFoundException::new);

        int userId = Objects.requireNonNull(getUser()).getUserId();
        mav.addObject("trip", trip);
        mav.addObject("userId", userId);
        mav.addObject("now", Timestamp.valueOf(LocalDateTime.now()));

        return mav;
    }

    @RequestMapping(value = "/trips/confirmTrip", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("id") int tripId) {
        User user = getUser();
        ts.confirmTrip(tripId, user.getUserId());
        if (Objects.equals(user.getRole(), "TRUCKER")) {
            LOGGER.info("Trip with Id: {} confirmed successfully by trucker", tripId);
            return new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripId);
        }
        else {
            LOGGER.info("Trip with Id: {} confirmed successfully by provider", tripId);
            return new ModelAndView("redirect:/trips/details?id=" + tripId);
        }
    }

    private User getUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof AuthUserDetailsImpl){
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).orElseThrow(UserNotFoundException::new);
        }
        return null;
    }

    @RequestMapping( value = "/trips/{tripId}/tripPicture", method = {RequestMethod.GET},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profilePicture(@PathVariable(value = "tripId") int tripId){
        return ts.getTripOrRequestById(tripId).orElseThrow(TripOrRequestNotFoundException::new).getImage().getImage();
    }


}
