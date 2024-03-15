package br.com.bpadash.params.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamEditEmployeeMaster {

    @NotBlank
    @Size(min = 3, max = 15, message = "O nome de usuário deve ter entre 3 a 15 caracteres")
    private String name;
    @NotBlank
    @Size(min = 10, max = 50, message = "O email do funcionário deve ter entre 10 a 50 caracteres")
    private String email;

    public ParamEditEmployeeMaster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
