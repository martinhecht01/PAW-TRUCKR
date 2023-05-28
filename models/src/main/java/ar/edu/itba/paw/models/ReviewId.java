package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Objects;

public class ReviewId implements Serializable {
    private User user;
    private Trip trip;

    public ReviewId() {
        // Default constructor required for composite identifiers
    }

    public ReviewId(User user, Trip trip) {
        this.user = user;
        this.trip = trip;
    }

    // Getters and setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    // Implement equals() and hashCode() methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(getUser(), reviewId.getUser()) &&
                Objects.equals(getTrip(), reviewId.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getTrip());
    }
}