package ar.edu.itba.paw.models;

import java.util.Base64;

public class Truck {
    private final String driverId; //Linked to User.id
    private final String licensePlate;
    private final Base64 photo; //Warning: Base64?

    public Truck(String driverId, String licensePlate, Base64 photo) {
        this.driverId = driverId;
        this.licensePlate = licensePlate;
        this.photo = photo;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Base64 getPhoto() {
        return photo;
    }
}
