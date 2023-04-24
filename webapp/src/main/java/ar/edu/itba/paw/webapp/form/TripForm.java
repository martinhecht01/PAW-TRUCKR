package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.annotations.DateValidator;
import ar.edu.itba.paw.webapp.annotations.PreventPast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;


@DateValidator
public class TripForm {
    @Pattern(regexp="^([A-Za-z]{3}\\d{3})|([A-Za-z]{2}\\d{3}[A-Za-z]{2})$")
    private  String licensePlate;

    @Pattern(regexp="^(5[0-9]|[6-9][0-9]|[1-9][0-9]{2,})$")
    private String availableWeight;

    @Pattern(regexp="^[1-9][0-9]*$")
    private String availableVolume;

    @Pattern(regexp="^[1-9][0-9]*$")
    private String price;

    //custom annotation para validar.
    @NotNull
    @PreventPast
    private String departureDate;

    @NotNull
    @PreventPast
    private  String arrivalDate;

    @NotNull
    @Size(min = 4)
    private String cargoType;

    @Size(min = 1, max = 100)
    private  String origin;

    @Size(min = 1, max = 100)
    private String destination;

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

    public void setPrice(String price) {this.price = price;}

    public String getPrice() {return price;}

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
