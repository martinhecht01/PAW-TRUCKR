package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Alert;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class AlertDto {
    private int id;
    private URI self;
    private String city;
    private Integer maxWeight;
    private Integer maxVolume;
    private String fromDate;
    private String toDate;
    private String cargoType;
    private URI user;

    public static AlertDto fromAlert(final UriInfo uri,final Alert alert){
        final AlertDto dto = new AlertDto();
        dto.id = alert.getAlertId();
        dto.self = uri.getBaseUriBuilder().path("/alerts/").path(alert.getAlertId().toString()).build();
        dto.city = alert.getCity();
        dto.maxWeight = alert.getMaxWeight();
        dto.maxVolume = alert.getMaxVolume();
        dto.fromDate = alert.getFromDate().toString();
        dto.toDate = alert.getToDate().toString();
        dto.cargoType = alert.getCargoType();
        dto.user = uri.getBaseUriBuilder().path("/users/").path(String.valueOf(alert.getUser().getUserId())).build();
        return dto;
    }


    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public String getCity() {
        return city;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public Integer getMaxVolume() {
        return maxVolume;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getId() {
        return id;
    }

    public URI getUser() {
        return user;
    }

    public String getCargoType() {
        return cargoType;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setMaxVolume(Integer maxVolume) {
        this.maxVolume = maxVolume;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public void setUser(URI user) {
        this.user = user;
    }
}
