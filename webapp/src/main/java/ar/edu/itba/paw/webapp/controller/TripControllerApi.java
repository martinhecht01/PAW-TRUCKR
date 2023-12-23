package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.*;
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

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

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

    @GET
    @Produces("application/vnd.tripList.v1+json")
    public Response getPublications(
            @QueryParam("userId") Integer userId,
            @QueryParam("status") @DefaultValue("ongoing") String status,
            @QueryParam("page") @DefaultValue(PAGE) int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize,
            @QueryParam("origin") String origin,
            @QueryParam("destination") String destination,
            @QueryParam("minAvailableVolume") Integer minAvailableVolume,
            @QueryParam("minAvailableWeight") Integer minAvailableWeight,
            @QueryParam("minPrice") Integer minPrice,
            @QueryParam("maxPrice") Integer maxPrice,
            @QueryParam("sortOrder") String sortOrder,
            @QueryParam("departureDate") String departureDate,
            @QueryParam("arrivalDate") String arrivalDate,
            @QueryParam("type") String type)
    {

        List <Trip> tripList;
        final User user;
        int maxPages;

        if(userId == null ){
            tripList = ts.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, page, pageSize);
            maxPages = ts.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
        }else {
            user = us.getUserById(userId).orElseThrow(UserNotFoundException::new);
            tripList = ts.getPublications(user.getUserId(), status, page);
            maxPages = ts.getTotalPagesPublications(user, status);
        }

        if (tripList.isEmpty()) {
            return Response.noContent().build();
        }

        List<TripDto> dtoList = tripList.stream().map(currifyUriInfo(TripDto::fromTrip)).collect(Collectors.toList());
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<TripDto>>(dtoList) {});
        PaginationHelper.getLinks(toReturn, uriInfo, page, maxPages);

        return toReturn.build();
    }

    //TODO error handling (si le mandas ids distintos a los que espera entra a tirar errores como loco)
    @PUT
    @Path("/{id}")
    public Response confirmTrip(@PathParam("id") int tripId) {
        User user = us.getUserById(1).orElseThrow(UserNotFoundException::new);

        Trip trip = ts.confirmTrip(tripId, user.getUserId(),LocaleContextHolder.getLocale());

        return Response.ok(TripDto.fromTrip(uriInfo, trip)).build();
    }
}
