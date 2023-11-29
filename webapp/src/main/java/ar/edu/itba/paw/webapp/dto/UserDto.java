package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UserDto {
    private String cuit;
    private URI self;

    private String name;
    private String email;

    private URI image;

    private URI trips;

    private URI itinerary;

    private URI reviews;

    public static UserDto fromUser(final UriInfo uri, final User user){
        final UserDto dto = new UserDto();
        dto.self = uri.getAbsolutePathBuilder().path("/users/").path(user.getUserId().toString()).build();
        dto.cuit = user.getCuit();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.trips = uri.getAbsolutePathBuilder().path("/trips/").path(user.getUserId().toString()).build();
        dto.itinerary = uri.getAbsolutePathBuilder().path("/itinerary/").path(user.getUserId().toString()).build();
        dto.reviews = uri.getAbsolutePathBuilder().path("/reviews/").path(user.getUserId().toString()).build();
        if(user.getImage() != null){
            dto.image = uri.getAbsolutePathBuilder().path("/images/").path(user.getImage().toString()).build();
        }
        return dto;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCuit() {
        return cuit;
    }

    public URI getImage() {
        return image;
    }

    public URI getItinerary() {
        return itinerary;
    }

    public URI getReviews() {
        return reviews;
    }

    public URI getTrips() {
        return trips;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setItinerary(URI itinerary) {
        this.itinerary = itinerary;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReviews(URI reviews) {
        this.reviews = reviews;
    }

    public void setTrips(URI trips) {
        this.trips = trips;
    }

}
