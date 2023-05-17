package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ConfirmPasswordValidation;

import javax.validation.constraints.Size;

@ConfirmPasswordValidation(passwordFieldName = "password", confirmPasswordFieldName = "repeatPassword")
public class ResetPasswordForm {
    @Size(min = 6, max = 100)
    private String password;

    private String repeatPassword;

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
