package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AcceptForm {
    @Size(min = 6, max = 100)
    private  String name;

    @Pattern(regexp = "^\\d{2}-\\d{8}-\\d{1}$")
    private  String cuit;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private  String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setCuit(String id) {
        this.cuit = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getCuit() {
        return cuit;
    }

    public String getEmail() {
        return email;
    }

}
