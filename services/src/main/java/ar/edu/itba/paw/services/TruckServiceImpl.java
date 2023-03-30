package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.TruckService;
import ar.edu.itba.paw.models.Truck;

import java.util.Base64;

public class TruckServiceImpl implements TruckService {
    @Override
    public Truck createTruck(String driverId, String licensePlate, Base64 photo) {
        return new Truck(driverId, licensePlate, photo);
    }
}
