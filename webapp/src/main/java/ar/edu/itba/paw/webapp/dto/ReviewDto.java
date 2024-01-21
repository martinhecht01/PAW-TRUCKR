package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import javax.persistence.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ReviewDto {

    private URI user;
    private URI self;
    private URI trip;

    private float rating;

    private String review;

    public static ReviewDto fromReview(UriInfo uriInfo, Review review){
        ReviewDto dto = new ReviewDto();
        dto.user = uriInfo.getBaseUriBuilder().path("/users/").path(String.valueOf(review.getUser().getUserId())).build();
        dto.self = uriInfo.getBaseUriBuilder().path("/reviews/").path(String.valueOf(review.getUserId())).path(String.valueOf(review.getTripId())).build();
        dto.trip = uriInfo.getBaseUriBuilder().path("/trips/").path(String.valueOf(review.getTrip().getTripId())).build();
        dto.rating = review.getRating();
        dto.review = review.getReview();
        return dto;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public URI getTrip() {
        return trip;
    }

    public void setTrip(URI trip) {
        this.trip = trip;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
