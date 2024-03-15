package br.com.bpadash.params.user;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamUserNameSession {

    @NotBlank
    @Size(min = 3, max = 15, message = "O nome de usuário deve ter entre 3 a 15 caracteres")
    private String name;

    public ParamUserNameSession() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
