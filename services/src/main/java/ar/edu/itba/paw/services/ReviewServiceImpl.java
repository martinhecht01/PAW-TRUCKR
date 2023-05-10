package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Review> optionalReview = reviewDao.getReviewByTripAndUserId(tripId,userId);

        return Optional.empty();
    }

    @Override
    public Optional<Review> getReviewByRequestAndUserId(int requestId, int userId) {
        return Optional.empty();
    }
}
