package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_reviewid_seq")
    @SequenceGenerator(sequenceName = "reviews_reviewid_seq", name = "reviews_reviewid_seq", allocationSize = 1)
    @Column(name = "reviewid")
    private Integer reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;


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

    public Review(Integer reviewId, Trip trip, User user, float rating, String review) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.review = review;
        this.user = user;
        this.trip = trip;
    }

    public Review( Trip trip, User user, float rating, String review) {
        this.reviewId = null;
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

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
}
