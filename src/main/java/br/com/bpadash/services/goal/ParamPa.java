package br.com.bpadash.services.goal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParamPa {
    @NotNull
    @Size(min = 2, max = 9, message = "Pa deve ter no máximo 9 caracteres")
    private String pa;

    public ParamPa() {
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }
}
