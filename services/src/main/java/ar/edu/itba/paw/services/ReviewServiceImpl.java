package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);


    private final ReviewDao reviewDao;
    private final TripDaoV2 tripDao;
    private final UserDao userDao;



    @Autowired
    public ReviewServiceImpl(@Qualifier("reviewDaoJPA") ReviewDao reviewDao, TripDaoV2 tripDao, @Qualifier("userDaoJPA") UserDao userDao) {
        this.reviewDao = reviewDao;
        this.tripDao = tripDao;
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getReviewByTripId(int tripId) {
        Optional<Trip> trip = tripDao.getTripOrRequestById(tripId);

        if (!trip.isPresent()){
            throw new ReviewNotFoundException();
        }

        return reviewDao.getReviewByTrip(trip.get());
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getReviewByTripAndUserId(int tripId, int userId) {
        Optional<User> user = userDao.getUserById(userId);
        Optional<Trip> trip = tripDao.getTripOrRequestById(tripId);
        LOGGER.info("Getting review for user {} in trip {}", userId, tripId);
        if (user.isPresent() && trip.isPresent())
            return reviewDao.getReviewByTripAndUserId(trip.get() ,user.get());

        return Optional.empty();
    }

    @Transactional
    @Override
    public Review createReview(int tripid, int userid, float rating, String comment) {
        Optional<User> user = userDao.getUserById(userid);
        Optional<Trip> trip = tripDao.getTripOrRequestById(tripid);
        LOGGER.info("Creating review for user {} in trip {}", userid, tripid);

        if (user.isPresent() && trip.isPresent())
            return reviewDao.createReview(trip.get(),user.get(),rating,comment);
        LOGGER.warn("Trip {} or user {} not present", tripid, userid);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Double getUserRating(int userId) {
        Optional<User> user = userDao.getUserById(userId);
        LOGGER.info("Getting rating for user {}", userId );

        if (user.isPresent())
            return reviewDao.getUserRating(user.get());

        return (double) 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> getUserReviews(int userId, int page, int pageSize) {

        Optional<User> user = userDao.getUserById(userId);

        LOGGER.info("Getting reviews for user {}", userId);

        if (user.isPresent())
            return reviewDao.getUserReviews(user.get(),page, pageSize);

        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    @Override
    public int getUserReviewCount(int userId) {
        Optional<User> user = userDao.getUserById(userId);
        LOGGER.info("Getting review count for user {}", userId);

        return user.map(reviewDao::getUserReviewCount).orElseThrow(UserNotFoundException::new);

    }
}
