package ar.edu.itba.paw.webapp.form;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfferForm {

    @NotNull(message = "validation.NotNull")
    @Size(min = 1, max = 250, message="validation.Description")
    private  String description;

    @Min(value=1, message="validation.Price.Min")
    @NotNull(message = "validation.NotNull}")
    private Integer price;

    @NotNull(message = "validation.NotNull")
    private Integer tripId;

    private Integer parent_offer_id;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getParent_offer_id() {
        return parent_offer_id;
    }

    public void setParent_offer_id(Integer parent_offer_id) {
        this.parent_offer_id = parent_offer_id;
    }
}
