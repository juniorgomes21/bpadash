package br.com.bpadash.params.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamLoginEmployee {

    @NotBlank
    @Size(min = 3, max = 15, message = "O nome de usuário deve ter entre 3 a 15 caracteres")
    private String userName;
    @NotBlank
    @Size(min = 8, max = 30, message = "A senha deve ter entre 8 a 30 caracteres")
    private String password;


    public ParamLoginEmployee() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
