package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.CargoType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.City;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class FilterForm {

    private static final String PAGE = "1";
    private static final String PAGE_SIZE = "12";

    @QueryParam("userId")
    private String userId;

    @QueryParam("tripOrRequest")
    @DefaultValue("TRIP")
    @Pattern(regexp="(TRIP|REQUEST)", message="validation.TripOrRequest")
    String tripOrRequest;

    @QueryParam("status")
    @DefaultValue("ACTIVE")
    @Pattern(regexp = "(EXPIRED|ACTIVE)", message="validation.Status")
    String status;

    @QueryParam("page")
    @DefaultValue(PAGE)
    int page;

    @QueryParam("pageSize")
    @DefaultValue(PAGE_SIZE)
    int pageSize;


    @Min(value = 1, message = "validation.Weight.Range")
    @Max(value = 100000, message = "validation.Weight.Range")
    @QueryParam("weight")
    private Integer weight;


    @Min(value = 1, message = "validation.Volume.Range")
    @Max(value = 1000, message = "validation.Volume.Range")
    @QueryParam("volume")
    private Integer volume;

    @CargoType
    @QueryParam("cargoType")
    private String cargoType;

    @Pattern(regexp="^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|)$", message="validation.DateFormat")
    @QueryParam("departureDate")
    private String departureDate;

    @Pattern(regexp="^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|)$", message="validation.DateFormat")
    @QueryParam("arrivalDate")
    private String arrivalDate;

    @City
    @Size(min = 0, max = 1000, message="validation.Origin")
    @QueryParam("origin")
    private  String origin;

    @City
    @Size(min = 0, max = 1000, message="validation.Destination")
    @QueryParam("destination")
    private String destination;

    @QueryParam("sortOrder")
    @Pattern(regexp="\\b(departureDate|arrivalDate|price)\\s+(ASC|DESC)\\b", message="validation.SortOrder")
    private String sortOrder;


    @Min(value = 0, message = "validation.MinPrice.Range")
    @Max(value = 1000000, message = "validation.MinPrice.Range")
    @QueryParam("minPrice")
    private Integer minPrice;


    @Min(value = 0, message = "validation.MaxPrice.Range")
    @Max(value = 1000000, message = "validation.MaxPrice.Range")
    @QueryParam("maxPrice")
    private Integer maxPrice;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTripOrRequest() {
        return tripOrRequest;
    }

    public void setTripOrRequest(String tripOrRequest) {
        this.tripOrRequest = tripOrRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
