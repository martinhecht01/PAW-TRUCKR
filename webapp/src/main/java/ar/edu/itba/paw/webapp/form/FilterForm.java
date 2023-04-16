package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class FilterForm {


    //custom annotation para validar.
    @Range(min = 1)
    private  int minAvailableWeight;
    @Range(min=1)
    private  int minAvailableVolume;


    private Date departureDate;
    private Date arrivalDate;

    @Size(min = 1, max = 100000)
    private  String origin;
    @Size(min = 1, max = 100000)
    private String destination;

    @Size(min = 1, max = 100000)
    private String sortOrder;

    @Range(min=0)
    private Integer minPrice;
    @Range(min=0)
    private Integer maxPrice;

    public Integer getMinAvailableWeight() {
        return minAvailableWeight;
    }

    public void setMinAvailableWeight(int minAvailableWeight) {
        this.minAvailableWeight = minAvailableWeight;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
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


    public int getAvailableWeight() {
        return minAvailableWeight;
    }

    public int getAvailableVolume() {
        return minAvailableVolume;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}
