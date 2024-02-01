package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class UserDto {
    private int id;
    private String cuit;
    private URI self;
    private String name;
    private URI image;
    private List<URI> trips;
    private String role;
    private URI reviews;
    private Double rating;

    public static UserDto fromUser(final UriInfo uri, final User user){
        final UserDto dto = new UserDto();
        dto.id = user.getUserId();
        dto.self = uri.getBaseUriBuilder().path("/users/").path(user.getUserId().toString()).build();
        dto.cuit = user.getCuit();
        dto.name = user.getName();
        dto.role = user.getRole();
        dto.rating = user.getRating();

        //TODO ver si esto es correcto
        dto.reviews = uri.getBaseUriBuilder().path("/reviews/").queryParam("userId",user.getUserId().toString()).build();

        if(user.getImage() != null){
            dto.image = uri.getBaseUriBuilder().path("/images/").path(String.valueOf(user.getImage().getImageid())).build();
        }
        return dto;
    }


    public String getName() {
        return name;
    }

    public String getCuit() {
        return cuit;
    }

    public URI getImage() {
        return image;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
