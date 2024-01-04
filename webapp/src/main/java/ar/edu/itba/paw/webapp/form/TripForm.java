package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.DateValidation;
import ar.edu.itba.paw.webapp.form.constraints.annotations.PreventPast;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.*;
import java.io.InputStream;

@DateValidation(start="departureDate", end="arrivalDate")
public class TripForm {

    @Pattern(regexp="^$|^([A-Za-z]{3}\\d{3})|([A-Za-z]{2}\\d{3}[A-Za-z]{2})$")
    @FormDataParam("licensePlate")
    private  String licensePlate;

    @Pattern(regexp="^(5[0-9]|[6-9][0-9]|[1-9][0-9]{2,})$")
    @FormDataParam("availableWeight")
    private String availableWeight;

    @Pattern(regexp="^[1-9][0-9]*$")
    @FormDataParam("availableVolume")
    private String availableVolume;

    @Pattern(regexp="^[1-9][0-9]*$")
    @FormDataParam("price")
    private String price;

    @Pattern(regexp="^(?!\\s*$).+")
    @PreventPast
    @FormDataParam("departureDate")
    private String departureDate;

    @PreventPast
    @Pattern(regexp="^(?!\\s*$).+")
    @FormDataParam("arrivalDate")
    private  String arrivalDate;

    @NotNull
    @FormDataParam("cargoType")
    private String cargoType;

    @Size(min = 1, max = 100)
    @FormDataParam("origin")
    private  String origin;

    @Size(min = 1, max = 100)
    @FormDataParam("destination")
    private String destination;

    @RequireImage
    @MaxFileSize(8)
    //TODO chequeo de tipo de imagen
//    @ImageType(types = {"image/jpeg", "image/png"})
    @FormDataParam("tripImage")
    private FormDataBodyPart tripImage;

    @FormDataParam("tripImage")
    private byte[] bytes;

    public FormDataBodyPart getTripImage() {
        return tripImage;
    }

    public void setTripImage(FormDataBodyPart tripImage) {
        this.tripImage = tripImage;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setAvailableWeight(String availableWeight) {
        this.availableWeight = availableWeight;
    }

    public void setAvailableVolume(String availableVolume) {
        this.availableVolume = availableVolume;
    }


    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPrice(String price) {this.price = price;}

    public String getPrice() {return price;}

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getAvailableWeight() {
        return availableWeight;
    }

    public String getAvailableVolume() {
        return availableVolume;
    }

    public String getCargoType() {
        return cargoType;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
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
}
