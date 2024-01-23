package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.exceptions.ReviewAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
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

//    @Transactional(readOnly = true)
//    @Override
//    public List<Review> getReviewByTripId(int tripId) {
//        Trip trip = tripDao.getTripOrRequestById(tripId).orElseThrow(TripOrRequestNotFoundException::new);
//        return reviewDao.getReviewByTrip(trip);
//    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getReviewById(Integer id){
        return reviewDao.getReviewById(id);
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
        User user = userDao.getUserById(userid).orElseThrow(UserNotFoundException::new);
        Trip trip = tripDao.getTripOrRequestById(tripid).orElseThrow(TripOrRequestNotFoundException::new);
        if(getReviewByTripAndUserId(tripid, userid).isPresent()){
            throw new ReviewAlreadyExistsException();
        }
        if(!trip.getTruckerConfirmation() || !trip.getProviderConfirmation()){
            throw new IllegalArgumentException();
        }
        LOGGER.info("Creating review for user {} in trip {}", userid, tripid);
        return reviewDao.createReview(trip, user, rating, comment);
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
