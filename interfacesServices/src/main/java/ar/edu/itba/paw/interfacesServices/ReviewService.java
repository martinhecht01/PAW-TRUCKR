package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;

public interface ReviewService {

    Optional<Review> getReviewByTripAndUserId(int tripId, int userId);
    Optional<Review> getReviewByRequestAndUserId(int requestId, int userId);


    void createReview(int tripid, int userid, float rating, String comment);
}
