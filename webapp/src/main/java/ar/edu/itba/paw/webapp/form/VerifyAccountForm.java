package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;

public class VerifyAccountForm {

    @Pattern(regexp="^[1-9][0-9]*$")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
