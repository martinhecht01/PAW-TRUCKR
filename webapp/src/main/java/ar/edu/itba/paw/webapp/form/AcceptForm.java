package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AcceptForm {
    @Size(min = 6, max = 100)
    private  String name;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^\\d{2}-\\d{8}-\\d{1}$")
    private  String id;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private  String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}