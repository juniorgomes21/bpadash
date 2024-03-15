package br.com.bpadash.params.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamCreateEmployee extends ParamUserNameSession {

    @NotBlank
    @Size(min = 10, max = 50, message = "O email do funcionário deve ter entre 10 a 50 caracteres")
    private String email;
    @NotBlank
    @Size(min = 8, max = 50, message = "A senha do funcionário deve ter entre 8 a 50 caracteres")
    private String password;
    @NotBlank
    @Size(min = 8, max = 50, message = "A senha do funcionário deve ter entre 8 a 50 caracteres")
    private String passwordConfirm;

    public ParamCreateEmployee() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
