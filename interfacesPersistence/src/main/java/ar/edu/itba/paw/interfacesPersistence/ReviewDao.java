package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Optional<Review> getReviewByTripAndUserId(Trip trip, User user);
    Optional<Review> getReviewByTrip(Trip trip);

    Review createReview(Trip trip, User user, float rating, String comment);

    Double getUserRating(User user);

    List<Review> getUserReviews(User user);
}
