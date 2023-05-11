package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }


    @Override
    public Optional<Review> getReviewByTripAndUserId(int tripId, int userId) {

        return reviewDao.getReviewByTripAndUserId(tripId,userId);
    }

    @Override
    public Optional<Review> getReviewByRequestAndUserId(int requestId, int userId) {
        return reviewDao.getReviewByTripAndUserId(requestId,userId);
    }

    @Override
    public void createReview(int tripid, int userid, float rating, String comment) {
         reviewDao.createReview(tripid,userid,rating,comment);
    }

    @Override
    public float getUserRating(int userId) {
        return reviewDao.getUserRating(userId);
    }

    @Override
    public List<Review> getUserReviews(int userId) {
        return reviewDao.getUserReviews(userId);
    }
}
