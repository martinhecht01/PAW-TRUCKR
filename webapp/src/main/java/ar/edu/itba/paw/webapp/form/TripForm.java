package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.*;

import javax.validation.constraints.*;

@DateValidation(start="departureDate", end="arrivalDate")
public class TripForm {

//    @NotNull(message = "validation.NotNull")
    @Pattern(regexp="^$|^([A-Za-z]{3}\\d{3})|([A-Za-z]{2}\\d{3}[A-Za-z]{2})$", message="validation.LicensePlate")
    @TruckerLicensePlate
    private  String licensePlate;

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp="^(5[0-9]|[6-9][0-9]|[1-9][0-9]{2,})$", message="validation.AvailableWeight")
    private String availableWeight;

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp="^[1-9][0-9]*$", message="validation.AvailableVolume")
    private String availableVolume;

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp="^[1-9][0-9]*$", message="validation.Price")
    private String price;

    @NotNull(message = "validation.NotNull")
    @PreventPast
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message="validation.DateFormat")
    private String departureDate;

    @NotNull(message = "validation.NotNull")
    @PreventPast
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message="validation.DateFormat")
    private  String arrivalDate;

    @NotNull(message = "validation.NotNull")
    @CargoType
    private String cargoType;

    @NotNull(message = "validation.NotNull")
    @City
    @Size(min = 1, max = 100, message="validation.Origin")
    private String origin;

    @NotNull(message = "validation.NotNull")
    @City
    @Size(min = 1, max = 100, message="validation.Destination")
    private String destination;

    @NotNull(message = "validation.NotNull")
    private Integer imageId;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setAvailableWeight(String availableWeight) {
        this.availableWeight = availableWeight;
    }

    public void setAvailableVolume(String availableVolume) {
        this.availableVolume = availableVolume;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getAvailableWeight() {
        return availableWeight;
    }

    public String getAvailableVolume() {
        return availableVolume;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
