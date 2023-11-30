package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class PastTripDto extends TripDto{

    private String tripUrl;
    private String userUrl;

    public static PastTripDto fromTripAndUserUrls(UriInfo tripUrl, Trip trip, User user) {
        PastTripDto pastTripDto = new PastTripDto();
        TripDto.fromTrip(tripUrl, trip);
        pastTripDto.setSelf(tripUrl.getBaseUriBuilder()
                .path("users")
                .path(String.valueOf(user.getUserId()))
                .path("trips")
                .path(String.valueOf(trip.getTripId()))
                .build());

        pastTripDto.tripUrl = tripUrl.getBaseUriBuilder().path("trips").path(String.valueOf(trip.getTripId())).build().toString();
        pastTripDto.userUrl = tripUrl.getBaseUriBuilder().path("users").path(String.valueOf(user.getUserId())).build().toString();
        return pastTripDto;
    }

    public static List<PastTripDto> fromPastTripList(UriInfo uriInfo, List<Trip> tripList, User user){
        return tripList.stream().map(a -> PastTripDto.fromTripAndUserUrls(uriInfo, a, user)).collect(Collectors.toList());
    }

    public String getTripUrl() {
        return tripUrl;
    }

    public void setTripUrl(String tripUrl) {
        this.tripUrl = tripUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
