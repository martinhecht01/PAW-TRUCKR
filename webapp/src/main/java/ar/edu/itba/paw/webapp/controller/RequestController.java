package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.*;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;

import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.RequestForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class RequestController {
    private final TripServiceV2 ts;

    private final ImageService is;

    private final CityService cs;

    private final UserService us;

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final ReviewService revs;

    @Autowired
    public RequestController(final TripServiceV2 ts, ImageService is, CityService cs, UserService us, ReviewService revs) {
        this.ts = ts;
        this.is = is;
        this.cs = cs;
        this.us = us;
        this.revs = revs;
    }

    @RequestMapping("/requests/browse")
    public ModelAndView browseRequests(@RequestParam(defaultValue = "1") String page,
                                    @RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String destination,
                                    @RequestParam(required = false) Integer minAvailableVolume,
                                    @RequestParam(required = false) Integer maxAvailableVolume,
                                    @RequestParam(required = false) Integer minAvailableWeight,
                                    @RequestParam(required = false) Integer maxAvailableWeight,
                                    @RequestParam(required = false) Integer minPrice,
                                    @RequestParam(required = false) Integer maxPrice,
                                    @RequestParam(required = false) String sortOrder,
                                    @RequestParam(required = false) String departureDate,
                                    @RequestParam(required = false) String arrivalDate,
                                    @RequestParam(required = false) String type)
    {
        LOGGER.info("Accessing browse requests page");
        Integer maxPages = ts.getActiveRequestsTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
        Integer currPage = Integer.parseInt(page);
        if(currPage < 1 || currPage > maxPages ){
            page = "1";
        }


        LOGGER.debug("MAX PAGES = {}", maxPages);

        final ModelAndView view = new ModelAndView("requests/browse");

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
        List<Trip> trips = ts.getAllActiveRequests(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, Integer.parseInt(page));

        LOGGER.debug("ACTIVE REQUESTS SIZE: {}  ",trips.size());
        view.addObject("offers", trips);
        return view;
    }


    @RequestMapping("/requests/create")
    public ModelAndView createRequest(@ModelAttribute("requestForm") final RequestForm form,
                                      @RequestParam(required = false) String origin,
                                      @RequestParam(required = false) String destination,
                                      @RequestParam(required = false) Integer minAvailableVolume,
                                      @RequestParam(required = false) Integer minAvailableWeight,
                                      @RequestParam(required = false) String departureDate,
                                      @RequestParam(required = false) String arrivalDate) {
        LOGGER.info("Accessing create requests page");
        final ModelAndView view = new ModelAndView("requests/create");
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate", arrivalDate);
        return view;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/requests/create", method = { RequestMethod.POST })
    public ModelAndView createRequest(@Valid @ModelAttribute("requestForm") final RequestForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.info("Error in create request form");
            return createRequest(form, form.getOrigin(), form.getDestination(), Integer.parseInt(form.getRequestedVolume()), Integer.parseInt(form.getRequestedWeight()), form.getMinDepartureDate(), form.getMaxArrivalDate());
        }

        LocalDateTime departure = LocalDateTime.parse(form.getMinDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getMaxArrivalDate());

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        Trip request = ts.createRequest(
                user.getUserId(),
                Integer.parseInt(form.getRequestedWeight()),
                Integer.parseInt(form.getRequestedVolume()),
                departure,
                arrival,
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                Integer.parseInt(form.getMaxPrice())
        );
        int imageid=is.uploadImage(form.getTripImage().getBytes());
        ts.updateTripPicture(request.getTripId(),imageid);

        LOGGER.info("Request created successfully");

        ModelAndView view = new ModelAndView("redirect:/requests/success?id="+request.getTripId());
        return view;
    }

    @RequestMapping("/requests/details")
    public ModelAndView requestDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm formReserve) {
        LOGGER.info("Accessing request details page");
        final ModelAndView mav = new ModelAndView("requests/details");
        LOGGER.info("Accessing request details page with id: {} ", id);
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping(value="/requests/sendReview", method = { RequestMethod.POST })
    public ModelAndView sendReview(@RequestParam("requestid") int requestid, @RequestParam("userid") int userid, @RequestParam ("rating") int rating, @RequestParam("description") String comment){
        User user = getUser();
        if (user == null){
            return new ModelAndView("redirect:/login");
        }
        revs.createReview(requestid, userid, rating, comment);
        if (Objects.equals(user.getRole(), "PROVIDER"))
            return new ModelAndView("redirect:/requests/manageRequest?requestId="+ requestid);
        else
            return new ModelAndView("redirect:/requests/details?id="+ requestid);
    }

    @RequestMapping(value = "/requests/cancelOffer", method = RequestMethod.POST)
    public ModelAndView cancelOffer(@ModelAttribute("offerId") final Integer offerId, @ModelAttribute("requestId") final Integer requestId) {
        LOGGER.info("Cancelling offer with id {}", offerId);
        ts.deleteOffer(offerId);
        return new ModelAndView("redirect:/requests/manageRequest?requestId=" + requestId);
    }

    @RequestMapping(value = "/requests/confirmRequest", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("requestId") int requestId) {
        User user = getUser();
        ts.confirmTrip(requestId, user.getUserId());
        if (Objects.equals(user.getRole(), "PROVIDER")) {
            LOGGER.info("Request with Id: {} confirmed successfully by provider", requestId);
            return new ModelAndView("redirect:/requests/manageRequest?requestId="+ requestId);
        }
        else {
            LOGGER.info("Request with Id: {} confirmed successfully by trucker", requestId);
            return new ModelAndView("redirect:/requests/details?id=" + requestId);
        }
    }

    @RequestMapping(value = "/requests/sendProposal", method = { RequestMethod.POST })
    public ModelAndView acceptProposal(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) throws MessagingException {
        if (errors.hasErrors()) {
            LOGGER.info("Error in accept form");
            return requestDetail(id, form);
        }

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        ts.createProposal(id, user.getUserId(), form.getDescription(), form.getPrice());
        LOGGER.info("Proposal created successfully");
        ModelAndView mav = new ModelAndView("redirect:/requests/reserveSuccess");

        mav.addObject("id",id);
        return mav;
    }
    @RequestMapping(value = "/requests/acceptProposal", method = { RequestMethod.POST })
    public ModelAndView acceptProposal(@RequestParam("proposalid") int proposalId, @RequestParam("requestid") int requestId) {
        ts.acceptProposal(proposalId);
        ModelAndView mav = new ModelAndView("requests/acceptSuccess");

        //TODO: HACER UNA VIEW CON ESTO + redirect
        Trip request = ts.getTripOrRequestByIdAndUserId(requestId, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        LOGGER.info("Proposal with Id: {} accepted successfully", proposalId);
        mav.addObject("request", request);
        return mav;
    }
    @RequestMapping("/requests/success")
    public ModelAndView requestDetail(@RequestParam("id") int id) {
        LOGGER.info("Accessing request success page");
        final ModelAndView mav = new ModelAndView("requests/success");
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }


    @RequestMapping("/requests/reserveSuccess")
    public ModelAndView requestReserveSuccess(@RequestParam("id") int id) {
        LOGGER.info("Accessing request reserve success page");
        final ModelAndView mav = new ModelAndView("requests/reserveSuccess");
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping("/requests/myRequests")
    public ModelAndView myRequests(@RequestParam(value = "acceptPage", required = false, defaultValue = "1") final Integer acceptPage, @RequestParam(value = "activePage", required = false, defaultValue = "1") final Integer activePage){
        User user = getUser();
        LOGGER.info("User: {} accessing my requests page", user.getCuit());

        Integer maxActivePage = ts.getTotalPagesActiveTripsOrRequests(user.getUserId());
        Integer maxAcceptPage = ts.getTotalPagesAcceptedTripsAndRequests(user.getUserId());

        if(activePage > maxActivePage || activePage < 1){
            maxActivePage = 1;
        }

        if(acceptPage > maxAcceptPage || acceptPage < 1){
            maxAcceptPage = 1;
        }

        final ModelAndView mav = new ModelAndView("requests/myRequests");
        mav.addObject("currentPageActive", activePage);
        mav.addObject("maxActivePage", maxActivePage);

        mav.addObject("currentPageAccepted", acceptPage);
        mav.addObject("maxAcceptedPage", maxAcceptPage);

        mav.addObject("expiredPublications",ts.getAllExpiredPublications(user.getUserId(), acceptPage));
        mav.addObject("activePublications", ts.getAllActivePublications(user.getUserId(), activePage));
        return mav;
    }

    @RequestMapping("/requests/manageRequest")
    public ModelAndView manageRequest(@RequestParam("requestId") int requestId, @ModelAttribute("acceptForm") final AcceptForm form ) {
        LOGGER.info("Accessing manage request page with request Id: {} ", requestId);
        final ModelAndView mav = new ModelAndView("requests/manageRequest");
        int userId = Objects.requireNonNull(getUser()).getUserId();
        Trip request = ts.getTripOrRequestByIdAndUserId(requestId, getUser()).orElseThrow(TripOrRequestNotFoundException::new);

        mav.addObject("now", Timestamp.valueOf(LocalDateTime.now()));
        mav.addObject("request", request);
        mav.addObject("userId", userId);
        return mav;
    }

    private User getUser() {

        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof AuthUserDetailsImpl){
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).orElseThrow(UserNotFoundException::new);
        }
        return null;
    }
}
