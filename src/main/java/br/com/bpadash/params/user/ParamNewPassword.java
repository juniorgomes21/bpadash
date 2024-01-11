package br.com.bpadash.params.user;

import javax.validation.constraints.NotBlank;

public class ParamNewPassword {
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String confPassword;

    public ParamNewPassword() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }
}
