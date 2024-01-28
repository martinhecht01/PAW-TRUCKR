package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;

public class ActionForm {

    @Pattern(regexp = "^(ACCEPT|REJECT)$", message = "validation.Action")
    @DefaultValue("ACCEPT")
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
