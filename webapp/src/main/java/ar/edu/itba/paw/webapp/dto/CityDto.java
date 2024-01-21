package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.City;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CityDto {

    Integer cityId;
    String cityName;
    URI self;

    public CityDto() {
        // Empty constructor needed by JAX-RS
    }

    public static CityDto fromCity(final UriInfo uri, final City city){
        CityDto dto = new CityDto();
        dto.self = uri.getBaseUriBuilder().path("/cities/").path(String.valueOf(city.getCityId())).build();
        dto.cityId = city.getCityId();
        dto.cityName = city.getCityName();
        return dto;
    }


    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}

