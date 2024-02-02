package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.controller.utils.EndpointsUri;
import org.springframework.web.util.UriComponentsBuilder;
import javax.ws.rs.core.UriInfo;



public class DescriptionLinkDto {

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

    public static DescriptionLinkDto fromDescriptionLink(final UriInfo uriInfo) {
        DescriptionLinkDto dto = new DescriptionLinkDto();

        UriComponentsBuilder ucb = UriComponentsBuilder.fromUri(uriInfo.getBaseUri());

        dto.alertsUri = ucb.cloneBuilder().path(EndpointsUri.Alerts.getUri()).build().toString();
        dto.cargoTypeUri = ucb.cloneBuilder().path(EndpointsUri.CargoType.getUri()).build().toString();
        dto.citiesUri = ucb.cloneBuilder().path(EndpointsUri.Cities.getUri()).build().toString();
        dto.imagesUri = ucb.cloneBuilder().path(EndpointsUri.Images.getUri()).build().toString();
        dto.publicationFilterUri = ucb.cloneBuilder().path(EndpointsUri.PublicationFilter.getUri()).build().toString();
        dto.reviewsUri = ucb.cloneBuilder().path(EndpointsUri.Reviews.getUri()).build().toString();
        dto.tripsFilterUri = ucb.cloneBuilder().path(EndpointsUri.TripsFilter.getUri()).build().toString();
        dto.tripsUri = ucb.cloneBuilder().path(EndpointsUri.Trips.getUri()).build().toString();
        dto.usersUri = ucb.cloneBuilder().path(EndpointsUri.Users.getUri()).build().toString();
        return dto;
    }

    @Override
    public int hashCode() {
        int result = usersUri != null ? usersUri.hashCode() : 0;
        result = 31 * result + (tripsUri != null ? tripsUri.hashCode() : 0);
        result = 31 * result + (publicationFilterUri != null ? publicationFilterUri.hashCode() : 0);
        result = 31 * result + (tripsFilterUri != null ? tripsFilterUri.hashCode() : 0);
        result = 31 * result + (citiesUri != null ? citiesUri.hashCode() : 0);
        result = 31 * result + (reviewsUri != null ? reviewsUri.hashCode() : 0);
        result = 31 * result + (cargoTypeUri != null ? cargoTypeUri.hashCode() : 0);
        result = 31 * result + (alertsUri != null ? alertsUri.hashCode() : 0);
        result = 31 * result + (imagesUri != null ? imagesUri.hashCode() : 0);
        return result;
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
