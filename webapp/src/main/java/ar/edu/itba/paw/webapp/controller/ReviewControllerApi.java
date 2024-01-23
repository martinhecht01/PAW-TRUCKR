package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("reviews")
@Component
public class ReviewControllerApi {

    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";


    private final ReviewService revs;
    private final UserService us;
    private final TripServiceV2 ts;


    @Autowired
    public ReviewControllerApi(final ReviewService revs, final UserService us, final TripServiceV2 ts) {
        this.revs = revs;
        this.us = us;
        this.ts = ts;
    }

    @Context
    private UriInfo uriInfo;

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @GET
    @Path("/{id:\\d+}")
    @Produces("application/vnd.review.v1+json")
    public Response getReview(@PathParam("id") final int id) {
        final Review review = revs.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        return Response.ok(ReviewDto.fromReview(uriInfo,review)).build();
    }


    @POST
    @Consumes("application/vnd.review.v1+json")
    @Produces("application/vnd.review.v1+json")
    @PreAuthorize("@accessHandler.userTripOwnerVerification(#form)")
    public Response createReview(@Valid ReviewForm form){
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException:: new);
        Trip trip = ts.getTripOrRequestById(form.getTripId()).orElseThrow(TripOrRequestNotFoundException::new);
        Review review;
        try {
            //TODO: esto es porque capaz no esta completo el viaje, se podra hacer mejor?
            Integer uId = Objects.equals(user.getUserId(), trip.getTrucker().getUserId()) ? trip.getProvider().getUserId() : trip.getTrucker().getUserId();

            review = revs.createReview(form.getTripId(), uId, form.getRating(), form.getReview());
        }catch (Exception e){
            throw new BadRequestException();
        }
        return Response.created(uriInfo.getBaseUriBuilder().path("/reviews/").build(review.getReviewId())).entity(ReviewDto.fromReview(uriInfo,review)).build();
    }



    @GET
    @Produces("application/vnd.reviewList.v1+json")
    public Response getUserReviews(
            @QueryParam("userId") Integer userId,
            @QueryParam("page") @DefaultValue(PAGE) int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize)
    {
        User user = us.getUserById(userId).orElseThrow(UserNotFoundException::new);
        List<Review> reviewList = revs.getUserReviews(user.getUserId(), page, pageSize);
        int maxPages = revs.getUserReviewCount(user.getUserId()) / pageSize + 1;

        if(reviewList.isEmpty()){
            return Response.noContent().build();
        }

        final List<ReviewDto> reviewDtos = reviewList.stream().map(currifyUriInfo(ReviewDto::fromReview)).collect(Collectors.toList());
        Response.ResponseBuilder toReturn = Response.ok( new GenericEntity<List<ReviewDto>>(reviewDtos){});
        PaginationHelper.getLinks(toReturn, uriInfo, page, maxPages);
        return toReturn.build();

    }


}
