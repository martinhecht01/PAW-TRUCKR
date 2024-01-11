package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("reviews")
@Component
public class ReviewControllerApi {

    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";



    private final ReviewService revs;
    private final UserService us;


    @Autowired
    public ReviewControllerApi(final ReviewService revs, final UserService us) {
        this.revs = revs;
        this.us = us;
    }

    @Context
    private UriInfo uriInfo;

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @GET
    @Path("/{userId}/{tripId}")
    @Produces("application/vnd.review.v1+json")
    public Response getReview(
            @PathParam("userId") final int userId,
            @PathParam("tripId") final int tripId) {
        final Review review = revs.getReviewByTripAndUserId(tripId, userId).orElseThrow(()->new RuntimeException("Review not found"));//TODO crear exeption
        return Response.ok(ReviewDto.fromReview(uriInfo,review)).build();
    }

    @POST
    @Consumes("application/vnd.review.v1+json")
    @Produces("application/vnd.review.v1+json")
    public Response createReview(@Valid ReviewForm form){
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException:: new);
        Review review = revs.createReview(form.getTripId(), user.getUserId(), form.getRating(), form.getReview());
        return Response.created(uriInfo.getBaseUriBuilder().path("/reviews/").build(review.getUserId(),review.getTripId())).entity(ReviewDto.fromReview(uriInfo,review)).build();
    }

    @GET
    @Produces("application/vnd.reviewList.v1+json")//trae las reviews por user
    public Response getReviews(
            @QueryParam("userId") int userId,
            @QueryParam("page") @DefaultValue(PAGE) int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize){
        final User user = us.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final List<Review> reviewList = revs.getUserReviews(user.getUserId());
        if(reviewList.isEmpty()){
            return Response.noContent().build();
        }
//        final int maxPages = revs.userReviewsMaxPages(user.getUserId()); TODO:paginar
        final List<ReviewDto> reviewDtos = reviewList.stream().map(currifyUriInfo(ReviewDto::fromReview)).collect(Collectors.toList());
        int maxPages = 1;
        Response.ResponseBuilder toReturn = Response.ok( new GenericEntity<List<ReviewDto>>(reviewDtos){});
        PaginationHelper.getLinks(toReturn, uriInfo, page, maxPages);
        return toReturn.build();

    }


}
