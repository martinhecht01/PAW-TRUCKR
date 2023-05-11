package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;

public interface ReviewDao {

    Optional<Review> getReviewByTripAndUserId(int tripId, int userId);
    Optional<Review> getReviewByRequestAndUserId(int tripId, int userId);


    void createReview(int tripid, int userid, float rating, String comment);
}
