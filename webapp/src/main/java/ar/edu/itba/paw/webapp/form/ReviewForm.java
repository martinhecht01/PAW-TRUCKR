package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewForm {

    @NotNull(message = "validation.NotNull")
    @Size(min = 1, max = 250, message="validation.Review")
    private  String review;

    @NotNull(message = "validation.NotNull")
    @Min(value=1, message = "validation.Rating.Min")
    @Max(value=5, message = "validation.Rating.Max")
    private float rating;

    @NotNull(message = "validation.NotNull")
    private int tripId;

    public String getReview() {
        return review;
    }

    public float getRating() {
        return rating;
    }

    public int getTripId() {
        return tripId;
    }
    public void setReview(String review) {
            this.review = review;
        }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
