package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReviewForm {//TODO:revisar si esta bien

        private  String review;
        private float rating;
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
