package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "tripid", nullable = false)
    private int tripId;

    @Id
    @Column(name = "userid", nullable = false)
    private int userId;

    @Column(name = "rating")
    private float rating;

    @Column(name = "review", length = 400)
    private String review;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tripid")
    private Trip trip;

    // Constructors, getters, and setters


    public Review(int tripId, int userId, float rating, String review, User user, Trip trip) {
        this.tripId = tripId;
        this.userId = userId;
        this.rating = rating;
        this.review = review;
        this.user = user;
        this.trip = trip;
    }

    public int getTripId() {
        return tripId;
    }

    public User getUser() {
        return user;
    }

    public Trip getTrip() {
        return trip;
    }


    public int getUserId() {
        return userId;
    }

    public String getReview() {
        return review;
    }

    public float getRating() {
        return rating;
    }
}
