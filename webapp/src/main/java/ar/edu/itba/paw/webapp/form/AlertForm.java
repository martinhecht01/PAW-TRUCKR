package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.*;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AlertDateValidation(start="fromDate", end="toDate")
public class AlertForm {

    @Range(min=1, max=100000, message = "validation.Weight.Range")
    private Integer maxWeight;

    @Range(min=1, max=1000, message = "validation.Volume.Range")
    private Integer maxVolume;

    @PreventPast
    @NotNull(message = "validation.NotNull")
    @Pattern(regexp="^(?!\\s*$).+", message="validation.fromDate")
    private String fromDate;

    @PreventPast
    @Pattern(regexp="^(?!\\s*$).+", message="validation.toDate")
    private  String toDate;

    @CargoType
    private String cargoType;

    @NotNull(message = "validation.NotNull")
    @City
    @Size(min = 1, max = 100, message = "validation.Origin")
    private  String origin;

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Integer maxVolume) {
        this.maxVolume = maxVolume;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
