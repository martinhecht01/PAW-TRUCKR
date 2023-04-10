package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class TripForm {

    @Size(min = 6, max = 100)
    private  String name;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^\\d{2}-\\d{8}-\\d{1}$")
    private  String id;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private  String email;
    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[A-Z]{2,3}\\d{3}$|^[A-Z]{2}\\d{3}[A-Z]{2}$\n")
    private  String licensePlate;

    //custom annotation para validar.
    private  int availableWeight;
    private  int availableVolume;


    private  String departureDate;
    private  String arrivalDate;

    private String cargoType;
    @Size(min = 1, max = 100000)
    private  String origin;
    @Size(min = 1, max = 100000)
    private String destination;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setAvailableWeight(int availableWeight) {
        this.availableWeight = availableWeight;
    }

    public void setAvailableVolume(int availableVolume) {
        this.availableVolume = availableVolume;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getAvailableWeight() {
        return availableWeight;
    }

    public int getAvailableVolume() {
        return availableVolume;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
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
}
