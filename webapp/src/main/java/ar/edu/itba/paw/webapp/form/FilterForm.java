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


//    private  String departureDate;
//    private  String arrivalDate;

    @Size(min = 1, max = 100000)
    private  String origin;
    @Size(min = 1, max = 100000)
    private String destination;



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
