package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import javax.persistence.*;

public class ReviewDto {

    private User user;

    private Trip trip;

    private float rating;

    private String review;

    public static ReviewDto fromReview(Trip trip, User user, float rating, String review){
        ReviewDto dto = new ReviewDto();
        dto.rating = rating;
        dto.review = review;
        dto.user = user;
        dto.trip = trip;
        return dto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
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
}
