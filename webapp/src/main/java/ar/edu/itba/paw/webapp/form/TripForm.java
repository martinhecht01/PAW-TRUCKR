package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class TripForm {
    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[A-Z]{2,3}\\d{3}$|^[A-Z]{2}\\d{3}[A-Z]{2}$\n")
    private  String licensePlate;

    @Size(min = 1, max = 100000)
    @Pattern(regexp = "\\d+")
    private  Number availableWeight;
    @Size(min = 1, max = 100000)
    @Pattern(regexp = "\\d+")
    private  Number availableVolume;

    private  Date departureDate;
    private  Date arrivalDate;
    @Size(min = 1, max = 100000)
    private  String origin;
    @Size(min = 1, max = 100000)
    private String destination;

}
