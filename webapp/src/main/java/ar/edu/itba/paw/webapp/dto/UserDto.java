package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private String cuit;
    private URI self;

    private String name;
    private String email;

    private URI image;

    private List<URI> trips;

    private URI itinerary;

    private URI reviews;

    public static UserDto fromUser(final UriInfo uri, final User user){
        final UserDto dto = new UserDto();
        dto.self = uri.getAbsolutePathBuilder().path("/users/").path(user.getUserId().toString()).build();
        dto.cuit = user.getCuit();
        dto.name = user.getName();
        dto.email = user.getEmail();
        String role = user.getRole();

        if(role.equals("TRUCKER"))
            dto.trips = user.getTruckerTrips().stream().map(t -> uri.getBaseUriBuilder().path("/trips/").path(Integer.toString(t.getTripId())).build()).collect(Collectors.toList());
        else if(role.equals("PROVIDER"))
            dto.trips = user.getProviderTrips().stream().map(t -> uri.getBaseUriBuilder().path("/trips/").path(Integer.toString(t.getTripId())).build()).collect(Collectors.toList());

        //dto.trips = uri.getBaseUriBuilder().path("/trips/").path(user.getUserId().toString()).build();
        dto.itinerary = uri.getBaseUriBuilder().path("/itinerary/").path(user.getUserId().toString()).build();
        dto.reviews = uri.getBaseUriBuilder().path("/reviews/").path(user.getUserId().toString()).build();
        if(user.getImage() != null){
            dto.image = uri.getBaseUriBuilder().path("/images/").path(String.valueOf(user.getImage().getImageid())).build();
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

    public List<URI> getTrips() {
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

    public void setTrips(List<URI> trips) {
        this.trips = trips;
    }

}
