package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class ReviewDaoJPA implements ReviewDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Review> getReviewByTripAndUserId(Trip trip, User user) {
        String jpql = "SELECT r FROM Review r WHERE r.trip = :trip AND r.user = :user";
        Review review = entityManager.createQuery(jpql, Review.class)
                .setParameter("trip", trip)
                .setParameter("user", user)
                .getSingleResult();
        return Optional.of(review);
    }

    @Override
    public void createReview(Trip trip, User user, float rating, String comment) {
        Review review = new Review(trip, user, rating, comment);
        LOGGER.info("Creating review token for user {} in trip {}", user.getUserId(), trip.getTripId());
        entityManager.persist(review);
    }

    @Override
    public float getUserRating(User user) {
        String jpql = "SELECT coalesce(avg(r.rating),0) FROM Review r WHERE r.user = :user";
        return entityManager.createQuery(jpql, float.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public List<Review> getUserReviews(User user) {
        String jpql = "SELECT r FROM Review r WHERE r.user = :user";
        return entityManager.createQuery(jpql, Review.class)
                .setParameter("user", user)
                .getResultList();
    }
}
