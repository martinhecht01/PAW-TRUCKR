package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Truck;

import java.util.Base64;

public interface TruckService {
    Truck createTruck(String driverId, String licensePlate, Base64 photo);
}
