package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;

public interface ReviewDao {

    Optional<Review> getReviewByTripAndUserId(int tripId, int userId);
    Optional<Review> getReviewByRequestAndUserId(int tripId, int userId);

    Review create(int tripId, int userId, float rating, String review);


}
