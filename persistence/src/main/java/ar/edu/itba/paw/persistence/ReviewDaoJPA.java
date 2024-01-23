package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class ReviewDaoJPA implements ReviewDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Review> getReviewByTripAndUserId(Trip trip, User user) {
        if(user == null)
            return Optional.empty();
        String jpql = "SELECT r FROM Review r WHERE r.trip = :trip AND r.user = :user";
        List<Review> reviews = entityManager.createQuery(jpql, Review.class)
                .setParameter("trip", trip)
                .setParameter("user", user)
                .getResultList();
        LOGGER.info("Creating review token for user {} in trip {}", user.getUserId(), trip.getTripId());
        return reviews.isEmpty() ? Optional.empty() : Optional.of(reviews.get(0));
    }

//    @Override
//    public List<Review> getReviewByTrip(Trip trip) {
////        if(trip == null)
////            return Optional.empty();
//        String jpql = "SELECT r FROM Review r WHERE r.trip = :trip ";
//        return entityManager.createQuery(jpql, Review.class)
//                .setParameter("trip", trip)
//                .getResultList();
////        return reviews.isEmpty() ? Optional.empty() : Optional.of(reviews.get(0));
//    }

    @Override
    public Optional<Review> getReviewById(Integer id){
        Review review = entityManager.find(Review.class, id);
        if (review == null) {
            return Optional.empty();
        }
        return Optional.of(review);
    }

    @Override
    public Review createReview(Trip trip, User user, float rating, String comment) {
        Review review = new Review(trip, user, rating, comment);
        LOGGER.info("Creating review token for user {} in trip {}", user.getUserId(), trip.getTripId());
        entityManager.persist(review);
        return review;
    }

    @Override
    public Double getUserRating(User user) {
        String jpql = "SELECT coalesce(avg(r.rating),0) FROM Review r WHERE r.user = :user";
        return entityManager.createQuery(jpql, Double.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public List<Review> getUserReviews(User user, int page, int pageSize) {
        String jpql = "SELECT r FROM Review r WHERE r.user = :user";
        return entityManager.createQuery(jpql, Review.class)
                .setParameter("user", user)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int getUserReviewCount(User user) {
        String jpql = "SELECT count(r) FROM Review r WHERE r.user = :user";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("user", user)
                .getSingleResult().intValue();
    }
}
