package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ConfirmPasswordValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@ConfirmPasswordValidation(passwordFieldName = "password", confirmPasswordFieldName = "repeatPassword")
public class UserForm {

    @Size(min = 6, max = 100)
    @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$")
    private String email;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[A-Za-z]+(\\s[A-Za-z]*)+$")
    private String name;

    @Size(min = 6, max = 100)
    private String password;


    private String repeatPassword;

    @Pattern(regexp = "20-[0-9]{8}-[0-9]")
    private String cuit;

    @NotNull
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRepeatPassword() {
        return repeatPassword;
    }
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}