package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CuitForm {

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp = "(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]", message="validation.Cuit")
    private String cuit;

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
}
