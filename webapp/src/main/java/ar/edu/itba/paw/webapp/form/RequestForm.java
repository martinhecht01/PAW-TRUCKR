package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.annotations.DateValidation;
import ar.edu.itba.paw.webapp.annotations.PreventPast;
import javax.validation.constraints.*;


@DateValidation(start="minDepartureDate", end="maxArrivalDate")
public class RequestForm {

    @Pattern(regexp = "^(5[0-9]|[6-9][0-9]|[1-9][0-9]{2,})$")
    private String requestedWeight;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String requestedVolume;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String maxPrice;

    @Pattern(regexp="^(?!\\s*$).+")
    @PreventPast
    private String minDepartureDate;

    @PreventPast
    @Pattern(regexp="^(?!\\s*$).+")
    private String maxArrivalDate;

    @NotNull
    private String cargoType;

    @Size(min = 1, max = 100)
    private String origin;

    @Size(min = 1, max = 100)
    private String destination;


    public void setRequestedWeight(String availableWeight) {
        this.requestedWeight = availableWeight;
    }

    public void setRequestedVolume(String availableVolume) {
        this.requestedVolume = availableVolume;
    }


    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMaxPrice(String price) {
        this.maxPrice = price;
    }

    public String getMaxPrice() {
        return maxPrice;
    }


    public String getRequestedWeight() {
        return requestedWeight;
    }

    public String getRequestedVolume() {
        return requestedVolume;
    }

    public String getCargoType() {
        return cargoType;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getMinDepartureDate() {
        return minDepartureDate;
    }

    public void setMinDepartureDate(String departureDate) {
        this.minDepartureDate = departureDate;
    }

    public String getMaxArrivalDate() {
        return maxArrivalDate;
    }

    public void setMaxArrivalDate(String arrivalDate) {
        this.maxArrivalDate = arrivalDate;
    }

}