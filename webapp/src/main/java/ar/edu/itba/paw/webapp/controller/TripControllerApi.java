package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.TripForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("trips")
@Component
public class TripControllerApi {

    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";

    private final UserService us;
    private final ImageService is;
    private final TripServiceV2 ts;
    private final ReviewService revs;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public TripControllerApi(final UserService us, ImageService is, ReviewService revs, TripServiceV2 ts){
        this.us = us;
        this.is = is;
        this.revs = revs;
        this.ts = ts;
    }


    @POST
    @Consumes("application/vnd.trip.v1+json")
    @Produces("application/vnd.trip.v1+json")
    public Response createTrip(@Valid TripForm form){
        LocalDateTime departure = LocalDateTime.parse(form.getDepartureDate());
        LocalDateTime arrival = LocalDateTime.parse(form.getArrivalDate());
        final User user = us.getUserById(1).orElseThrow(UserNotFoundException::new);//TODO: get user from session ver como hacemos
        final Trip trip = ts.createTrip(
                user.getUserId(),
                form.getLicensePlate(),
                Integer.parseInt(form.getAvailableWeight()),
                Integer.parseInt(form.getAvailableVolume()),
                departure,
                arrival,
                form.getOrigin(),
                form.getDestination(),
                form.getCargoType(),
                Integer.parseInt(form.getPrice()));
        return Response.created(uriInfo.getBaseUriBuilder().path("/trips/" ).path(String.valueOf(trip.getTripId())).build()).entity(TripDto.fromTrip(uriInfo,trip)).build();
    }

    @GET
    @Produces("application/vnd.trip.v1+json")
    @Path("/{tripId}")
    public Response getTrip(@PathParam("tripId") int tripId){
        final Trip trip = ts.getTripOrRequestById(tripId).orElseThrow(TripOrRequestNotFoundException::new);
        return Response.ok(TripDto.fromTrip(uriInfo,trip)).build();
    }

    //    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    otra manera con un costom mimetype
//    @Produces("applictation/vnd.userlist.v1+json")
//    public Response listUsers(final int page){
//          final List<User> userList = us.getAll(page);
//          if (userList.isEmpty()){
//              return Response.noContent().build();
//          }
//          return Response.ok(userList).
//              .link("", "next")
//              .link("", "prev")
//              .link("", "first")
//              .link("", "last")
//              .build();
//    }

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @GET
    public Response getActivePublications(@QueryParam("userId") int userId,
                                          @QueryParam("page") @DefaultValue(PAGE) int page,
                                          @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize){

        final User user = us.getUserById(userId).orElseThrow(UserNotFoundException::new);
        List<Trip> tripList = ts.getAllActivePublications(user.getUserId(),page);

        if(tripList.isEmpty()){
            return Response.noContent().build();
        }

        List<TripDto> dtoList = tripList.stream().map(currifyUriInfo(TripDto::fromTrip)).collect(Collectors.toList());
        int maxPages = ts.getTotalPagesActivePublications(user);

        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<TripDto>>(dtoList) {})
                .link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", 1).build(), "first")
                .link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", maxPages).build(), "last");

        if(page != maxPages){
            toReturn.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page + 1).build(), "next");
        }

        if(page != 1){
            toReturn.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page - 1).build(), "prev");
        }

        return toReturn.build();
    }


//    @RequestMapping("/trips/manageTrip")
//    public ModelAndView manageTrip(@RequestParam("tripId") int tripId, @ModelAttribute("acceptForm") final AcceptForm form ) {
//        LOGGER.info("Accessing manage trip page with trip Id: {}", tripId);
//        final ModelAndView mav = new ModelAndView("trips/manageTrip");
//        Trip trip = ts.getTripOrRequestByIdAndUserId(tripId, getUser()).orElseThrow(TripOrRequestNotFoundException::new);
//
//        int userId = Objects.requireNonNull(getUser()).getUserId();
//        mav.addObject("trip", trip);
//        mav.addObject("userId", userId);
//        mav.addObject("now", LocalDateTime.now());
//
//        return mav;
//    }
    @POST
    @Path("/confirmTrip")
    public ModelAndView confirmTrip(@RequestParam("id") int tripId) {
        User user = us.getUserById(1).orElseThrow(UserNotFoundException::new);
        ts.confirmTrip(tripId, user.getUserId(),LocaleContextHolder.getLocale());
        if (Objects.equals(user.getRole(), "TRUCKER")) {
//            LOGGER.info("Trip with Id: {} confirmed successfully by trucker", tripId);
            return new ModelAndView("redirect:/trips/manageTrip?tripId=" + tripId);
        }
        else {
//            LOGGER.info("Trip with Id: {} confirmed successfully by provider", tripId);
//            LOGGER.info("Trip with Id: {} confirmed successfully by provider", tripId);
            return new ModelAndView("redirect:/trips/details?id=" + tripId);
        }
    }
}
