package br.com.bpadash.params.adm;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamTestEmailUser {

    @NotBlank
    @Email
    @Size(min = 11, max = 50)
    private String email;

    @NotBlank
    @Size(min = 5, max = 5)
    private String code;


    public ParamTestEmailUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
