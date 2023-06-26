package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.*;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;

import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.RequestForm;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @RequestMapping("/requests/browse")
//    public ModelAndView browseRequests(@RequestParam(defaultValue = "1") String page,
//                                    @RequestParam(required = false) String origin,
//                                    @RequestParam(required = false) String destination,
//                                    @RequestParam(required = false) Integer minAvailableVolume,
//                                    @RequestParam(required = false) Integer maxAvailableVolume,
//                                    @RequestParam(required = false) Integer minAvailableWeight,
//                                    @RequestParam(required = false) Integer maxAvailableWeight,
//                                    @RequestParam(required = false) Integer minPrice,
//                                    @RequestParam(required = false) Integer maxPrice,
//                                    @RequestParam(required = false) String sortOrder,
//                                    @RequestParam(required = false) String departureDate,
//                                    @RequestParam(required = false) String arrivalDate,
//                                    @RequestParam(required = false) String type)
//    {
//        LOGGER.info("Accessing browse requests page");
//        Integer maxPages = ts.getActiveRequestsTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
//        Integer currPage = Integer.parseInt(page);
//        if(currPage < 1 || currPage > maxPages ){
//            page = "1";
//        }
//
//
//        LOGGER.debug("MAX PAGES = {}", maxPages);
//
//        final ModelAndView view = new ModelAndView("requests/browse");
//
//        view.addObject("maxPage", maxPages);
//        view.addObject("currentPage", page);
//        view.addObject("origin",origin);
//        view.addObject("destination",destination);
//        view.addObject("minAvailableVolume",minAvailableVolume);
//        view.addObject("minAvailableWeight",minAvailableWeight);
//        view.addObject("minPrice",minPrice);
//        view.addObject("maxPrice",maxPrice);
//        view.addObject("sortOrder",sortOrder);
//        view.addObject("departureDate",departureDate);
//        view.addObject("arrivalDate",arrivalDate);
//        List<Trip> trips = ts.getAllActiveRequests(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, Integer.parseInt(page));
//
//        LOGGER.debug("ACTIVE REQUESTS SIZE: {}  ",trips.size());
//        view.addObject("offers", trips);
//        return view;
//    }

//    @RequestMapping(value = "/media/films")
//    public ModelAndView films(HttpServletRequest request,@RequestParam(value = "page", defaultValue = "1") final int page,
//                              @Valid @ModelAttribute("filterForm") final FilterForm filterForm,
//                              final BindingResult errors) throws ParseException {
//        LOGGER.debug("Trying to access films");
//        if(errors.hasErrors()){
//            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
//            return new ModelAndView("redirect: " + request.getHeader("referer"));
//        }
//        final ModelAndView mav = new ModelAndView("principal/primary/films");
//        final List<Genre> genres = filterForm.getGenres().stream().map(g -> g.replaceAll("\\s+", "")).map(Genre::valueOf).collect(Collectors.toList());
//        final List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.FILMS);
//        final PageContainer<Media> mostLikedFilms = favoriteService.getMostLikedMedia(MediaType.FILMS, 0, itemsPerContainer);
//        final PageContainer<Media> mediaListContainer = mediaService.getMediaByFilters(mediaTypes,page-1,itemsPerPage, SortType.valueOf(filterForm.getSortType().toUpperCase()),genres,filterForm.getDecade(), filterForm.getLastYear());
//        mav.addObject("mostLikedFilms", mostLikedFilms.getElements());
//        mav.addObject("mediaListContainer", mediaListContainer);
//        final List<String> decades = new ArrayList<>();
//        decades.add("ALL");
//        for (Integer i : IntStream.range(0, 11).map(x -> (10 * x) + 1920).toArray()) {
//            decades.add(Integer.toString(i));
//        }
//        mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).map(String::toUpperCase).collect(Collectors.toList()));
//        mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).map(String::toUpperCase).collect(Collectors.toList()));
//        mav.addObject("decadesType", decades);
//        LOGGER.info("Access to films successfully");
//        return mav;

    @RequestMapping("/requests/browse")
    public ModelAndView browse( @RequestParam(defaultValue = "1") String page, @Valid @ModelAttribute("filterForm") FilterForm ff, final BindingResult errors){

        final ModelAndView view = new ModelAndView("requests/browse");

        if(errors.hasErrors()){
            List<Trip> trips = new ArrayList<>();
            view.addObject("offers", trips);
            view.addObject("errors", errors);
            LOGGER.debug("Error filtering trips");
            return view;
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

       // String page= "1";
        Integer maxPages = ts.getActiveRequestsTotalPages(ff.getOrigin(), ff.getDestination(),ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), depDate, arrDate, ff.getType());

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

        view.addObject("offers", ts.getAllActiveRequests(ff.getOrigin(), ff.getDestination(), ff.getAvailableVolume(), ff.getMinAvailableWeight(), ff.getMinPrice(), ff.getMaxPrice(), ff.getSortOrder(), depDate, arrDate, ff.getType(), Integer.parseInt(page)));

        return view;
    }


    @RequestMapping("/requests/create")
    public ModelAndView createRequest(@ModelAttribute("requestForm") final RequestForm form,
                                      @RequestParam(required = false) String origin,
                                      @RequestParam(required = false) String destination,
                                      @RequestParam(required = false) Integer minAvailableVolume,
                                      @RequestParam(required = false) Integer minAvailableWeight,
                                      @RequestParam(required = false) String departureDate,
                                      @RequestParam(required = false) String arrivalDate,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) Integer suggestedPrice) {
        final ModelAndView view = new ModelAndView("requests/create");
        view.addObject("origin",origin);
        view.addObject("destination",destination);
        view.addObject("minAvailableVolume",minAvailableVolume);
        view.addObject("minAvailableWeight",minAvailableWeight);
        view.addObject("departureDate",departureDate);
        view.addObject("arrivalDate", arrivalDate);
        view.addObject("type", type);
        view.addObject("suggestedPrice",suggestedPrice);
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
            return createRequest(form,
                    form.getOrigin(),
                    form.getDestination(),
                    (form.getRequestedVolume() == null || form.getRequestedVolume().isEmpty()) ? 0 : Integer.parseInt(form.getRequestedVolume()),
                    (form.getRequestedWeight() == null || form.getRequestedWeight().isEmpty()) ? 0 : Integer.parseInt(form.getRequestedWeight()),
                    (form.getMinDepartureDate() == null || form.getMinDepartureDate().isEmpty()) ? null : form.getMinDepartureDate(),
                    (form.getMaxArrivalDate() == null || form.getMaxArrivalDate().isEmpty()) ? null : form.getMaxArrivalDate(),
                    form.getCargoType(),
                    (form.getMaxPrice() == null || form.getMaxPrice().isEmpty()) ? 0 : Integer.parseInt(form.getMaxPrice()));
        }

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        Trip request = ts.createRequest(
                user.getUserId(),
                (form.getRequestedWeight() == null || form.getRequestedWeight().isEmpty()) ? 0 : Integer.parseInt(form.getRequestedWeight()),
                (form.getRequestedVolume() == null || form.getRequestedVolume().isEmpty()) ? 0 : Integer.parseInt(form.getRequestedVolume()),
                (form.getMinDepartureDate() == null || form.getMinDepartureDate().isEmpty()) ? null : LocalDateTime.parse(form.getMinDepartureDate()),
                (form.getMaxArrivalDate() == null || form.getMaxArrivalDate().isEmpty()) ? null : LocalDateTime.parse(form.getMaxArrivalDate()),
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                (form.getMaxPrice() == null || form.getMaxPrice().isEmpty()) ? 0 : Integer.parseInt(form.getMaxPrice()),
                LocaleContextHolder.getLocale()
        );
        int imageid=is.uploadImage(form.getTripImage().getBytes());
        ts.updateTripPicture(request.getTripId(),imageid);

        LOGGER.info("Request created successfully for user: {}, requestId: {}", user.getUserId(), request.getTripId());

        ModelAndView view = new ModelAndView("redirect:/requests/success?id="+request.getTripId());
        return view;
    }

    @RequestMapping("/requests/details")
    public ModelAndView requestDetail(@RequestParam("id") int id, @ModelAttribute("acceptForm") final AcceptForm formReserve) {
        final ModelAndView mav = new ModelAndView("requests/details");
        LOGGER.info("Accessing request details page with id: {} ", id);
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping(value = "/requests/confirmRequest", method = { RequestMethod.POST })
    public ModelAndView confirmTrip(@RequestParam("requestId") int requestId) {
        User user = getUser();
        ts.confirmTrip(requestId, user.getUserId(),LocaleContextHolder.getLocale());

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
    public ModelAndView sendProposal(@RequestParam("id") int id, @Valid @ModelAttribute("acceptForm") final AcceptForm form, final BindingResult errors) throws MessagingException {
        if (errors.hasErrors()) {
            LOGGER.debug("Error in accept form");
            return requestDetail(id, form);
        }

        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.getUserByCuit(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        ts.createProposal(id, user.getUserId(), form.getDescription(), form.getPrice(), LocaleContextHolder.getLocale());
        LOGGER.info("Proposal created successfully for requestId: {} and userId: {}", id, user.getUserId());
        ModelAndView mav = new ModelAndView("redirect:/requests/reserveSuccess");

        mav.addObject("id",id);
        return mav;
    }

    @RequestMapping("/requests/acceptSuccess")
    public ModelAndView acceptSuccess(@RequestParam("requestId") String tripId){
        ModelAndView mav = new ModelAndView("requests/acceptSuccess");
        Trip trip = ts.getTripOrRequestByIdAndUserId(Integer.parseInt(tripId), getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", trip);
        return mav;
    }

    @RequestMapping("/requests/success")
    public ModelAndView requestDetail(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("requests/success");
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }


    @RequestMapping("/requests/reserveSuccess")
    public ModelAndView requestReserveSuccess(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("requests/reserveSuccess");
        Trip request = ts.getTripOrRequestByIdAndUserId(id, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
        mav.addObject("request", request);
        return mav;
    }

    @RequestMapping("/requests/myRequests")
    public ModelAndView myRequests(@RequestParam(value = "acceptPage", required = false, defaultValue = "1") final Integer acceptPage, @RequestParam(value = "activePage", required = false, defaultValue = "1") final Integer activePage, @RequestParam(required = false) Boolean activeSecondTab){
        User user = getUser();

        Integer maxActivePage = ts.getTotalPagesActivePublications(user);
        Integer maxAcceptPage = ts.getTotalPagesExpiredPublications(user);

        final ModelAndView mav = new ModelAndView("requests/myRequests");
        mav.addObject("currentPageActive", activePage < 0 || activePage > maxActivePage ? 1 : activePage);
        mav.addObject("maxActivePage", maxActivePage);

        mav.addObject("currentPageAccepted", acceptPage < 0 || acceptPage > maxAcceptPage ? 1 : acceptPage);
        mav.addObject("maxAcceptedPage", maxAcceptPage);

        mav.addObject("expiredPublications",ts.getAllExpiredPublications(user.getUserId(), acceptPage < 0 || acceptPage > maxAcceptPage ? 1 : acceptPage));
        mav.addObject("activePublications", ts.getAllActivePublications(user.getUserId(), activePage < 0 || activePage > maxActivePage ? 1 : activePage));

        mav.addObject("activeSecondTab", activeSecondTab != null && activeSecondTab);

        return mav;
    }

    @RequestMapping("/requests/manageRequest")
    public ModelAndView manageRequest(@RequestParam("requestId") int requestId, @ModelAttribute("acceptForm") final AcceptForm form ) {
        LOGGER.info("Accessing manage request page with request Id: {} ", requestId);
        final ModelAndView mav = new ModelAndView("requests/manageRequest");
        int userId = Objects.requireNonNull(getUser()).getUserId();
        Trip request = ts.getTripOrRequestByIdAndUserId(requestId, getUser()).orElseThrow(TripOrRequestNotFoundException::new);

        mav.addObject("now", LocalDateTime.now());
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
