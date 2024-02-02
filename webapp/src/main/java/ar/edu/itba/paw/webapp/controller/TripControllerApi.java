package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.auth.handlers.AccessHandler;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.PublicationDto;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.AuthErrorException;
import ar.edu.itba.paw.webapp.form.*;
import ar.edu.itba.paw.webapp.function.CurryingFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;
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
    public Response createTrip( @Valid TripForm form ) {
        Integer availableWeight;
        Integer availableVolume;
        LocalDateTime departure;
        LocalDateTime arrival;
        Integer price;
        try {
            departure = LocalDateTime.parse(form.getDepartureDate());
            arrival = LocalDateTime.parse(form.getArrivalDate());
            availableWeight = Integer.parseInt(form.getAvailableWeight());
            availableVolume = Integer.parseInt(form.getAvailableVolume());
            price = Integer.parseInt(form.getPrice());
        } catch (Exception e) {
            throw new BadRequestException();
        }

        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final Trip trip = ts.createTrip(user, form.getLicensePlate(), availableWeight, availableVolume, departure,arrival, form.getOrigin(), form.getDestination(), form.getCargoType(), price);

        ts.updateTripPicture(trip.getTripId(), form.getImageId());
        return Response.created(uriInfo.getBaseUriBuilder().path("/trips/").path(String.valueOf(trip.getTripId())).build()).entity(TripDto.fromTrip(uriInfo, trip)).build();
    }


    @GET
    @Produces("application/vnd.trip.v1+json")
    @Path("/{id:\\d+}")
    @PreAuthorize("@accessHandler.isTripOwner(#id)")
    public Response getTrip(@PathParam("id") int id){
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Trip trip = ts.getTripOrRequestByIdAndUserId(id, user);
        return Response.ok(TripDto.fromTrip(uriInfo, trip)).build();
    }

    @GET
    @Produces("application/vnd.publication.v1+json")
    @Path("/{id:\\d+}")
    public Response getPublication(@PathParam("id") int id){
        final Trip trip = ts.getTripOrRequestById(id).orElseThrow(TripOrRequestNotFoundException::new);
        return Response.ok(PublicationDto.fromTrip(uriInfo, trip)).build();
    }

    @GET
    @Produces("application/vnd.tripList.v1+json")
    @PreAuthorize("@accessHandler.getAuth()")
    public Response getTrips(
            @QueryParam("status") @DefaultValue("ONGOING") @Pattern(regexp = "^(ONGOING|PAST|FUTURE)$", message="validation.Status") String status,
            @QueryParam("page") @DefaultValue(PAGE) int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize)
    {
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        List <Trip> tripList = ts.getTrips(user.getUserId(), status, page, pageSize);
        int maxPages = ts.getTotalPagesTrips(user, status);
        if (tripList.isEmpty()) {
            return Response.noContent().build();
        }
        List<TripDto> dtoList = tripList.stream().map(currifyUriInfo(TripDto::fromTrip)).collect(Collectors.toList());
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<TripDto>>(dtoList) {});
        PaginationHelper.getLinks(toReturn, uriInfo, page, maxPages);
        return toReturn.build();
    }

    @GET
    @Produces("application/vnd.publicationList.v1+json")
    public Response getPublications(@Valid @BeanParam FilterForm form) {
        List <Trip> tripList;
        final User user;
        int maxPages;
        Integer userId;

        try{
            userId = Integer.parseInt(form.getUserId());
        }catch (Exception e){
            throw new BadRequestException();
        }

        if(form.getUserId() == null ){
            tripList = ts.getAllActiveTripsOrRequests(form.getOrigin(), form.getDestination(), form.getVolume(), form.getWeight(), form.getMinPrice(), form.getMaxPrice(), form.getSortOrder(), form.getDepartureDate(), form.getArrivalDate(), form.getCargoType(), form.getTripOrRequest(), form.getPage(), form.getPageSize());
            maxPages = ts.getActiveTripsOrRequestsTotalPages(form.getOrigin(), form.getDestination(), form.getVolume(), form.getWeight(), form.getMinPrice(), form.getMaxPrice(), form.getDepartureDate(), form.getArrivalDate(), form.getCargoType(), form.getTripOrRequest());
        }else {
            user = us.getUserById(userId).orElseThrow(UserNotFoundException::new);
            tripList = ts.getPublications(user.getUserId(), form.getStatus(), form.getPage());
            maxPages = ts.getTotalPagesPublications(user, form.getStatus());
        }

        if (tripList.isEmpty()) {
            return Response.noContent().build();
        }

        List<PublicationDto> dtoList = tripList.stream().map(currifyUriInfo(PublicationDto::fromTrip)).collect(Collectors.toList());
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<PublicationDto>>(dtoList) {});
        PaginationHelper.getLinks(toReturn, uriInfo, form.getPage(), maxPages);
        return toReturn.build();
    }

    @PATCH
    @Path("/{id:\\d+}")
    @PreAuthorize("@accessHandler.isTripOwner(#id)")
    public Response confirmTrip(@PathParam("id") int id) {
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        try{
            ts.confirmTrip(id, user, LocaleContextHolder.getLocale());
        }catch (IllegalArgumentException e){
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }


    @DELETE
    @Path("{id:\\d+}")
    @PreAuthorize("@accessHandler.isTripOwner(#id)")
    public Response deletePublication(@PathParam("id") int id){
        Trip publication = ts.getTripOrRequestById(id).orElseThrow(TripOrRequestNotFoundException::new);
        try {
            ts.deletePublication(publication);
        }catch(IllegalArgumentException e){
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }
}
