package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.*;
import com.sun.istack.internal.Nullable;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


//@DateValidation(start="departureDate", end="arrivalDate")
public class AlertForm {
    @Range(min=1, max=100000)
    private Integer maxWeight;

    @Range(min=1, max=10000)
    private Integer maxVolume;

    @Pattern(regexp="^(?!\\s*$).+")
    @PreventPast
    private String fromDate;
    
    @Pattern(regexp="^(\\s*|.+)$")
    //@PreventPast
    private  String toDate;

    @NotNull
    @Size(min = 1, max = 100)
    private  String origin;

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

    public LocalDateTime getFromDate() {
        if(fromDate == null || fromDate.isEmpty()){
            return null;
        }
        return LocalDateTime.parse(fromDate);
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        if (toDate == null || toDate.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(toDate);
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
