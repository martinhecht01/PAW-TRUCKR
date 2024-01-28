package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.CargoType;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CargoTypeDto {

    Integer cargoTypeId;
    String cargoTypeName;
    URI self;

    public CargoTypeDto() {
        // Empty constructor needed by JAX-RS
    }

    public static CargoTypeDto fromCargoType(final UriInfo uri, final CargoType cargoType){
        CargoTypeDto dto = new CargoTypeDto();

        dto.self = uri.getBaseUriBuilder().path("/cargoType/").path(String.valueOf(cargoType.getCargoTypeId())).build();
        dto.cargoTypeId = cargoType.getCargoTypeId();
        dto.cargoTypeName = cargoType.getName();
        return dto;
    }

    public Integer getCargoTypeId() {
        return cargoTypeId;
    }

    public void setCargoTypeId(Integer cargoTypeId) {
        this.cargoTypeId = cargoTypeId;
    }

    public String getCargoTypeName() {
        return cargoTypeName;
    }

    public void setCargoTypeName(String cargoTypeName) {
        this.cargoTypeName = cargoTypeName;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}

