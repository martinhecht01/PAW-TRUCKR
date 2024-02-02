package ar.edu.itba.paw.webapp.controller.utils;

public enum EndpointsUri {

    Alerts("/alerts{/alertId}"),
    CargoType("/cargoTypes{/cargoTypeId}"),
    Cities("/cities{/cityId}"),
    Users("/users{/userId}"),
    Images("/images{/imageId}"),
    Reviews("/reviews{/reviewId}"),
    Trips("/trips{/tripId}"),
    PublicationFilter("/trips?userId={userId}&tripOrRequest={tripOrRequest}&status={status}" +
            "&page={page}&pageSize={pageSize}&weight={weight}&volume={volume}&cargoType={cargoType}" +
            "&departureDate={departureDate}&arrivalDate={arrivalDate}&origin={origin}" +
            "&destination={destination}&sortOrder={sortOrder}&minPrice={minPrice}&maxPrice={maxPrice}"),
    TripsFilter("/trips?status={tripId}&page={page}&pageSize={pageSize}");


    private final String uri;

    EndpointsUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

}
