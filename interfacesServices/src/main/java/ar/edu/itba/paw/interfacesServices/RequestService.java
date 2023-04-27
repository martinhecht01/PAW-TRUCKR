package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RequestService {
    Request createRequest(String cuit,
                          int availableWeight,
                          int availableVolume,
                          LocalDateTime departureDate,
                          LocalDateTime arrivalDate,
                          String origin,
                          String destination,
                          String type,
                          int price);

    List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    Optional<Request> getRequestById(int tripid);

    Request acceptRequest(int tripId, String email, String name, String cuit);

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    List<Request> getAllActiveRequestsByUserId(Integer userid);
}
