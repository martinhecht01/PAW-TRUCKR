package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VerifyAccountForm {
    @Pattern(regexp="^[1-9][0-9]*$")
    @Size(min = 6, max = 6)
    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
