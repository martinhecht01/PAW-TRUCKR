package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.interfacesServices.RequestService;
import ar.edu.itba.paw.interfacesServices.UserService;
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
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RequestController {

    private final RequestService rs;
    private final UserService us;
    private final CityService cs;

    @Autowired
    public RequestController(final RequestService rs, final UserService us, final CityService cs){
        this.rs = rs;
        this.us = us;
        this.cs = cs;
    }

    @RequestMapping("/browseRequests")
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

        final ModelAndView view = new ModelAndView("landing/browseRequests");
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


    @RequestMapping("/create/request")
    public ModelAndView createRequest(@ModelAttribute("requestForm") final RequestForm form) {
        final ModelAndView view = new ModelAndView("landing/createRequest");
        return view;
    }

    @ModelAttribute("cities")
    public List<String> getCities() {
        return cs.getAllCities();
    }


    @RequestMapping(value = "/create/request", method = { RequestMethod.POST })
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

    @RequestMapping("/requestDetail")
    public ModelAndView requestDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm form) {
        final ModelAndView mav = new ModelAndView("landing/requestDetails");
        Request request = rs.getRequestById(id).orElseThrow(RequestNotFoundException::new);
        mav.addObject("request", request);
        mav.addObject("user", us.getUserById(request.getUserId()));
        return mav;
    }

//    @RequestMapping(value = "/accept", method = { RequestMethod.POST })
//    public ModelAndView accept(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) {
//        if (errors.hasErrors()) {
//            return requestDetail(id, form);
//        }
//
//        rs.acceptRequest(id, form.getEmail(),form.getName(),form.getCuit());
//
//        return new ModelAndView("redirect:/browseRequests");
//    }

    @RequestMapping("/requests/success")
    public ModelAndView requestDetail(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("landing/requestSuccess");
        Request request = rs.getRequestById(id).orElseThrow(RequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }
//
//    @RequestMapping("/requests/myRequests")
//    public ModelAndView myRequests(){
//        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
//        final ModelAndView mav = new ModelAndView("landing/myRequests");
//        mav.addObject("offers", rs.getAllActiveRequestsByUserId(user.getUserId()));
//        return mav;
//    }


}
