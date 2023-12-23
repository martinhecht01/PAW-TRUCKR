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
//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class TripController {

    private final TripServiceV2 ts;
    private final UserService us;
    private final CityService cs;

    private final ImageService is;

    private final static Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    @Autowired
    public TripController(final TripServiceV2 ts, final UserService us, final CityService cs, ImageService is){
        this.ts = ts;
        this.us = us;
        this.cs = cs;
        this.is = is;
    }

    @RequestMapping("/trips/search")
    public ModelAndView searchGet(@ModelAttribute("filterForm") final FilterForm form){
        final ModelAndView mav = new ModelAndView("trips/searchTrip");
        return mav;
    }

    @RequestMapping(value = "/trips/search", method = RequestMethod.POST)
    public ModelAndView searchPost(@Valid @ModelAttribute("filterForm") final FilterForm form, final BindingResult result){
        if(result.hasErrors()){
            return searchGet(form);
        }

        return new ModelAndView("forward:/trips/search/results");
    }

    @RequestMapping("/trips/search/results")
    public ModelAndView searchResults(@RequestParam(defaultValue = "1") String page, @Valid @ModelAttribute("filterForm") final FilterForm ff, final BindingResult result){
        final ModelAndView view = new ModelAndView("trips/results");

        String arrDate;
        String depDate;
        if(ff.getArrivalDate() == null || ff.getArrivalDate().equals(""))
            arrDate = "";
        else
            arrDate = ff.getArrivalDate();

        if (ff.getDepartureDate() == null || ff.getDepartureDate().equals(""))
            depDate = "";
        else
            depDate = ff.getDepartureDate();

        Integer maxPages = ts.getActiveTripsTotalPages(ff.getOrigin(), ff.getDestination(),ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), depDate, arrDate, ff.getType());

        Integer currPage = Integer.parseInt(page);
        if(currPage < 1 || currPage > maxPages ){
            page = "1";
        }

        view.addObject("maxPage", maxPages);
        view.addObject("currentPage", page);
        view.addObject("origin",ff.getOrigin());
        view.addObject("destination",ff.getDestination());
        view.addObject("minAvailableVolume",ff.getAvailableVolume());
        view.addObject("minAvailableWeight",ff.getMinAvailableWeight());
        view.addObject("minPrice",ff.getMinPrice());
        view.addObject("maxPrice",ff.getMaxPrice());
        view.addObject("sortOrder",ff.getSortOrder());
        view.addObject("departureDate",ff.getDepartureDate());
        view.addObject("arrivalDate",ff.getArrivalDate());
        view.addObject("cargoType",ff.getType());

        view.addObject("offers", ts.getAllActiveTrips(ff.getOrigin(), ff.getDestination(), ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), ff.getSortOrder(), depDate, arrDate, ff.getType(), Integer.parseInt(page), 12));

        return view;
    }

    @RequestMapping(value = "/trips/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tripForm") final TripForm form, final BindingResult errors) {
        if (errors.hasErrors() || Objects.requireNonNull(getUser()).getUserId() == null) {
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
        LOGGER.info("Trip created successfully for user: {}, tripid: {} ", user.getUserId(), trip.getTripId());
        return new ModelAndView("redirect:/trips/success?id="+trip.getTripId());
    }

    @RequestMapping("/trips/browse")
    public ModelAndView browse(@RequestParam(defaultValue = "1") String page,
                               @Valid @ModelAttribute("filterForm") FilterForm ff,
                               final BindingResult errors) {
        final ModelAndView view = new ModelAndView("trips/browse");

        if(errors.hasErrors()){
            List<Trip> requests = new ArrayList<>();
            view.addObject("offers", requests);
            view.addObject("errors", errors);
            return  view;
        }

        String arrDate;
        String depDate;
        if(ff.getArrivalDate() == null || ff.getArrivalDate().equals(""))
            arrDate = "";
        else
            arrDate = ff.getArrivalDate();

        if (ff.getDepartureDate() == null || ff.getDepartureDate().equals(""))
            depDate = "";
        else
            depDate = ff.getDepartureDate();

        Integer maxPages = ts.getActiveTripsTotalPages(ff.getOrigin(), ff.getDestination(),ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), depDate, arrDate, ff.getType());

        Integer currPage = Integer.parseInt(page);
        if(currPage < 1 || currPage > maxPages ){
            page = "1";
        }

        view.addObject("maxPage", maxPages);
        view.addObject("currentPage", page);
        view.addObject("origin",ff.getOrigin());
        view.addObject("destination",ff.getDestination());
        view.addObject("minAvailableVolume",ff.getAvailableVolume());
        view.addObject("minAvailableWeight",ff.getMinAvailableWeight());
        view.addObject("minPrice",ff.getMinPrice());
        view.addObject("maxPrice",ff.getMaxPrice());
        view.addObject("sortOrder",ff.getSortOrder());
        view.addObject("departureDate",ff.getDepartureDate());
        view.addObject("arrivalDate",ff.getArrivalDate());
        view.addObject("type",ff.getType());

        view.addObject("offers", ts.getAllActiveTrips(ff.getOrigin(), ff.getDestination(), ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), ff.getSortOrder(), depDate, arrDate, ff.getType(), Integer.parseInt(page), 12));

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

        ts.createProposal(id, user.getUserId(), form.getDescription(), form.getPrice(), LocaleContextHolder.getLocale());
        LOGGER.info("Proposal with Id: {} sent successfully", id);
        ModelAndView mav = new ModelAndView("redirect:/trips/reserveSuccess");
        mav.addObject("id", id);
        return mav;
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
    public ModelAndView myTrips(@RequestParam(value = "acceptPage", required = false, defaultValue = "1") final Integer acceptPage,
                                @RequestParam(value = "activePage", required = false, defaultValue = "1") final Integer activePage,
                                @RequestParam(required = false) Boolean activeSecondTab){
        User user = getUser();
        Integer maxActivePage = ts.getTotalPagesActivePublications(user);
        Integer maxAcceptPage = ts.getTotalPagesExpiredPublications(user);

        final ModelAndView mav = new ModelAndView("trips/myTrips");
        mav.addObject("currentPageActive", activePage < 0 || activePage > maxActivePage ? 1 : activePage);
        mav.addObject("maxActivePage", maxActivePage);

        mav.addObject("currentPageAccepted", acceptPage < 0 || acceptPage > maxAcceptPage ? 1 : acceptPage);
        mav.addObject("maxAcceptedPage", maxAcceptPage);
        mav.addObject("expiredPublications",ts.getAllExpiredPublications(user.getUserId(), acceptPage < 0 || acceptPage > maxAcceptPage ? 1 : acceptPage));
        mav.addObject("activePublications", ts.getAllActivePublications(user.getUserId(), activePage < 0 || activePage > maxActivePage ? 1 : activePage));

        mav.addObject("activeSecondTab", activeSecondTab != null && activeSecondTab);
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
        mav.addObject("now", LocalDateTime.now());

        return mav;
    }

    @RequestMapping(value = "/trips/confirmTrip", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("id") int tripId) {
        User user = getUser();
        ts.confirmTrip(tripId, user.getUserId(),LocaleContextHolder.getLocale());
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
