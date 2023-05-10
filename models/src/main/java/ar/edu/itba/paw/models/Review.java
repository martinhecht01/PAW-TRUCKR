package ar.edu.itba.paw.models;

public class Review {

    private final int tripId;

    private final int userId;

    private final float rating;

    private final String review;


    public Review(int tripId, int userId, float rating, String review) {
        this.rating=rating;
        this.review=review;
        this.tripId=tripId;
        this.userId=userId;
    }
}
