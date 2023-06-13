package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

public class FilterForm {

    //custom annotation para validar.

    @Min(1)
    @Max(1000000)
    private  Integer minAvailableWeight;


    @Range(min = 1, max=1000000)
    private  Integer minAvailableVolume;


    private String type;


    private String departureDate;


    private String arrivalDate;


    @Size(min = 0, max = 1000)
    private  String origin;


    @Size(min = 0, max = 1000)
    private String destination;


    @Size(min = 1, max = 100000)
    private String sortOrder;


    @Range(min=0, max=999999)
    private Integer minPrice;


    @Range(min=0, max=1000000)
    private Integer maxPrice;

    public Integer getMinAvailableWeight() {
        return minAvailableWeight;
    }

    public void setMinAvailableWeight(int minAvailableWeight) {
        this.minAvailableWeight = minAvailableWeight;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setAvailableWeight(int availableWeight) {
        this.minAvailableWeight = availableWeight;
    }

    public void setAvailableVolume(int availableVolume) {
        this.minAvailableVolume = availableVolume;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public Integer getAvailableWeight() {
        return minAvailableWeight;
    }

    public Integer getAvailableVolume() {
        return minAvailableVolume;
    }

    public void setMinAvailableWeight(Integer minAvailableWeight) {
        this.minAvailableWeight = minAvailableWeight;
    }

    public Integer getMinAvailableVolume() {
        return minAvailableVolume;
    }

    public void setMinAvailableVolume(Integer minAvailableVolume) {
        this.minAvailableVolume = minAvailableVolume;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}
