package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Path("trips")
@Component
public class TripControllerApi {
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
        final Trip trip = ts.getTripOrRequestById(tripId).get();
        return Response.ok(TripDto.fromTrip(uriInfo,trip)).build();
    }
}
