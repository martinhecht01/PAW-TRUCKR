package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;
import java.util.List;

public interface ReviewService {

    Optional<Review> getReviewByTripAndUserId(int tripId, int userId);


    Review createReview(int tripid, int userid, float rating, String comment);

    Double getUserRating(int userId);

    List<Review> getUserReviews(int userId);
}
