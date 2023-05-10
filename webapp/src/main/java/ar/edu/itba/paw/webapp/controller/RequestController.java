package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.*;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.RequestNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class RequestController {

    private final RequestService rs;
    private final UserService us;
    private final CityService cs;

    private final TripService ts;

    private final ReviewService revs;

    @Autowired
    public RequestController(final RequestService rs, final UserService us, final CityService cs, final TripService ts, final ReviewService revs) {
        this.rs = rs;
        this.us = us;
        this.cs = cs;
        this.ts = ts;
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
                                    @RequestParam(required = false) String arrivalDate)
    {
        Integer maxPages = rs.getTotalPages(origin, destination,minAvailableVolume,maxAvailableVolume, minAvailableWeight, maxAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
        Integer currPage = Integer.parseInt(page);
        if(Integer.parseInt(page) < 1 || Integer.parseInt(page) > maxPages ){
            page = "1";
        }

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
        view.addObject("maxAvailableWeight", maxAvailableWeight);
        view.addObject("maxAvailableVolume", maxAvailableVolume);
        List<Request> requests = rs.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate,maxAvailableVolume,maxAvailableWeight, Integer.parseInt(page));
        view.addObject("offers", requests);
        return view;
    }


    @RequestMapping("/requests/create")
    public ModelAndView createRequest(@ModelAttribute("requestForm") final RequestForm form) {
        final ModelAndView view = new ModelAndView("requests/create");
        return view;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/requests/create", method = { RequestMethod.POST })
    public ModelAndView createReq(@Valid @ModelAttribute("requestForm") final RequestForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return createRequest(form);
        }

        LocalDateTime departure = LocalDateTime.parse(form.getMinDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getMaxArrivalDate());

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        System.out.println(form.getOrigin() + "FORM RESULT");

        Request request = rs.createRequest(
                user.getCuit(),
                Integer.parseInt(form.getRequestedWeight()),
                Integer.parseInt(form.getRequestedVolume()),
                departure,
                arrival,
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                Integer.parseInt(form.getMaxPrice())
        );
        ModelAndView view = new ModelAndView("redirect:/requests/success?id="+request.getRequestId());
        return view;
    }

    @RequestMapping("/requests/details")
    public ModelAndView requestDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm formReserve) {
        final ModelAndView mav = new ModelAndView("requests/details");
        Request request = rs.getRequestById(id).orElseThrow(RequestNotFoundException::new);
        User user = getUser();

        if (user != null){
            mav.addObject("reviewed", revs.getReviewByRequestAndUserId(id, user.getUserId()).orElseThrow(RequestNotFoundException::new)); //TODO: fijarse si existe una review para este request de este usuario
            mav.addObject("user", us.getUserById(request.getUserId()).orElseThrow(UserNotFoundException :: new));
            mav.addObject("userId", getUser().getUserId());
        }
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping(value = "/requests/confirmRequest", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("requestId") int requestId) {
        User user = getUser();
        rs.confirmRequest(requestId, user.getUserId());
        if (Objects.equals(user.getRole(), "PROVIDER"))
            return new ModelAndView("redirect:/requests/manageRequest?requestId="+ requestId);
        else
            return new ModelAndView("redirect:/requests/details?id="+ requestId);
    }

    @RequestMapping(value = "/requests/sendProposal", method = { RequestMethod.POST })
    public ModelAndView accept(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) throws MessagingException {
        if (errors.hasErrors()) {
            return requestDetail(id, form);
        }

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        try {
            rs.sendProposal(id, user.getUserId(), form.getDescription());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        ModelAndView mav = new ModelAndView("redirect:/requests/reserveSuccess");

        mav.addObject("id",id);
        return mav;
    }
    @RequestMapping(value = "/requests/acceptProposal", method = { RequestMethod.POST })
    public ModelAndView acceptProposal(@RequestParam("proposalid") int proposalId, @RequestParam("requestid") int requestId) {
        System.out.println("accepting proposal ID = " + proposalId);
        rs.acceptRequest(proposalId);
        final ModelAndView mav = new ModelAndView("requests/acceptSuccess");
        Request request = rs.getRequestById(requestId).orElseThrow(RequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }
    @RequestMapping("/requests/success")
    public ModelAndView requestDetail(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("requests/success");
        Request request = rs.getRequestById(id).orElseThrow(RequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }


    @RequestMapping("/requests/reserveSuccess")
    public ModelAndView requestReserveSuccess(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("requests/reserveSuccess");
        Request request = rs.getRequestById(id).orElseThrow(RequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping("/requests/myRequests")
    public ModelAndView myRequests(){
        User user = getUser();
        final ModelAndView mav = new ModelAndView("requests/myRequests");
        mav.addObject("acceptedRequests",rs.getAllAcceptedRequestsByUserId(user.getUserId()) );
        mav.addObject("acceptedTrips", ts.getAllActiveTripsByAcceptUserId(user.getUserId()));
        mav.addObject("myRequests", rs.getAllActiveRequestsAndProposalCount(user.getUserId()));
        return mav;
    }

    @RequestMapping("/requests/manageRequest")
    public ModelAndView manageRequest(@RequestParam("requestId") int requestId, @ModelAttribute("acceptForm") final AcceptForm form ) {
        final ModelAndView mav = new ModelAndView("requests/manageRequest");
        int userId = getUser().getUserId();
        Request request = rs.getRequestByIdAndUserId(requestId, userId).orElseThrow(RequestNotFoundException::new);
        if(request.getAcceptUserId() > 0) {
            mav.addObject("acceptUser", us.getUserById(request.getAcceptUserId()).orElseThrow(UserNotFoundException::new));
            mav.addObject("reviewed", revs.getReviewByRequestAndUserId(requestId, userId).orElseThrow(RequestNotFoundException::new)); //TODO: fijarse si existe una review para este request de este usuario
        }
        System.out.println("ACCEPT UID = " + request.getAcceptUserId());
        System.out.println("PROPOSAL COUNT = " +  rs.getProposalsForRequestId(request.getRequestId()).size());
        mav.addObject("request", request);
        mav.addObject("userId", userId);
        mav.addObject("offers", rs.getProposalsForRequestId(request.getRequestId()));
        return mav;
    }

    @RequestMapping(value = "/requests/active")
    public ModelAndView activeRequests(){
        ModelAndView mav = new ModelAndView("requests/active");
        User user  = getUser();
        List<Request> requests =  rs.getAllRequestsInProgressByAcceptUserId(user.getUserId());
        mav.addObject("requests", requests);
        List<User> providers = new ArrayList<>();

        for (Request request: requests ) {
            providers.add( us.getUserById(request.getUserId()).orElseThrow(UserNotFoundException :: new));
        }

        mav.addObject("providers", providers);
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
