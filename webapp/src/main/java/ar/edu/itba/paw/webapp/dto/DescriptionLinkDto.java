package ar.edu.itba.paw.webapp.dto;

public class DescriptionLinkDto {
    private final static String BASE_URI = "http://localhost:8080/api";

    String usersUri;
    String tripsUri;
    String publicationFilterUri;
    String tripsFilterUri;
    String citiesUri;
    String reviewsUri;
    String cargoTypeUri;
    String alertsUri;
    String imagesUri;

    public DescriptionLinkDto() {
    }

    public static DescriptionLinkDto fromDescriptionLink() {
        DescriptionLinkDto dto = new DescriptionLinkDto();
        dto.alertsUri = BASE_URI + "/alerts{/alertId}";
        dto.cargoTypeUri = BASE_URI + "/cargoTypes{/cargoTypeId}";
        dto.citiesUri = BASE_URI + "/cities{/cityId}";
        dto.imagesUri = BASE_URI + "/images{/imageId}";
        dto.publicationFilterUri = BASE_URI + "/trips?userId={userId}&tripOrRequest={tripOrRequest}&status={status}" +
                "&page={page}&pageSize={pageSize}&weight={weight}&volume={volume}&cargoType={cargoType}" +
                "&departureDate={departureDate}&arrivalDate={arrivalDate}&origin={origin}" +
                "&destination={destination}&sortOrder={sortOrder}&minPrice={minPrice}&maxPrice={maxPrice}";
        dto.reviewsUri = BASE_URI + "/reviews{/reviewId}";
        dto.tripsFilterUri = BASE_URI + "/trips?status={tripId}&page={page}&pageSize={pageSize}";
        dto.tripsUri = BASE_URI + "/trips{/tripId}";
        dto.usersUri = BASE_URI + "/users{/userId}";
        return dto;
    }

    public String getUsersUri() {
        return usersUri;
    }

    public void setUsersUri(String usersUri) {
        this.usersUri = usersUri;
    }

    public String getTripsUri() {
        return tripsUri;
    }

    public void setTripsUri(String tripsUri) {
        this.tripsUri = tripsUri;
    }

    public String getPublicationFilterUri() {
        return publicationFilterUri;
    }

    public void setPublicationFilterUri(String publicationFilterUri) {
        this.publicationFilterUri = publicationFilterUri;
    }

    public String getTripsFilterUri() {
        return tripsFilterUri;
    }

    public void setTripsFilterUri(String tripsFilterUri) {
        this.tripsFilterUri = tripsFilterUri;
    }

    public String getCitiesUri() {
        return citiesUri;
    }

    public void setCitiesUri(String citiesUri) {
        this.citiesUri = citiesUri;
    }

    public String getReviewsUri() {
        return reviewsUri;
    }

    public void setReviewsUri(String reviewsUri) {
        this.reviewsUri = reviewsUri;
    }

    public String getCargoTypeUri() {
        return cargoTypeUri;
    }

    public void setCargoTypeUri(String cargoTypeUri) {
        this.cargoTypeUri = cargoTypeUri;
    }

    public String getAlertsUri() {
        return alertsUri;
    }

    public void setAlertsUri(String alertsUri) {
        this.alertsUri = alertsUri;
    }

    public String getImagesUri() {
        return imagesUri;
    }

    public void setImagesUri(String imagesUri) {
        this.imagesUri = imagesUri;
    }
}
