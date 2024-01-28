package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ConfirmPasswordValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@ConfirmPasswordValidation(passwordFieldName = "password", confirmPasswordFieldName = "repeatPassword")
public class UserForm {

    @NotNull(message = "validation.NotNull")
    @Size(min = 6, max = 100, message = "validation.Email")
    @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$", message="validation.Email")
    private String email;

    @NotNull(message = "validation.NotNull")
    @Size(min = 6, max = 100, message = "validation.Name.Size")
    @Pattern(regexp = "^[A-Za-z]+(\\s[A-Za-z]*)+$", message="validation.Name.Pattern")
    private String name;

    @Size(min = 6, max = 100, message = "validation.Password.Size")
    private String password;

    private String repeatPassword;

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp = "(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]", message = "validation.Cuit")
    private String cuit;

    @NotNull(message = "validation.NotNull")
    @Pattern(regexp = "^(PROVIDER|TRUCKER)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "validation.Role")
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