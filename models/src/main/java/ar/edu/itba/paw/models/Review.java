package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@IdClass(ReviewId.class)
public class Review {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripid", nullable = false)
    private Trip trip;

    @Column(name = "rating")
    private float rating;

    @Column(name = "review", length = 400)
    private String review;



    // Constructors, getters, and setters
    public Review() {
        // Default constructor required by Hibernate
    }

    public Review(Trip trip, User user, float rating, String review) {
        this.rating = rating;
        this.review = review;
        this.user = user;
        this.trip = trip;
    }

    public int getTripId() {
        return trip.getTripId();
    }

    public User getUser() {
        return user;
    }

    public Trip getTrip() {
        return trip;
    }


    public int getUserId() {
        return user.getUserId();
    }

    public String getReview() {
        return review;
    }

    public float getRating() {
        return rating;
    }
}
