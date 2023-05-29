package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

public class FilterForm {


    //custom annotation para validar.
    @Null
    @Range(min = 1, max=10000)
    private  int minAvailableWeight;

    @Null
    @Range(min = 1, max=10000)
    private  int minAvailableVolume;

    @Null
    private Date departureDate;

    @Null
    private Date arrivalDate;

    @Null
    @Size(min = 1, max = 1000)
    private  String origin;

    @Null
    @Size(min = 1, max = 1000)
    private String destination;

    @Null
    @Size(min = 1, max = 100000)
    private String sortOrder;

    @Null
    @Range(min=0, max=999999)
    private Integer minPrice;

    @Null
    @Range(min=0, max=1000000)
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
