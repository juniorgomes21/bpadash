package br.com.bpadash.params.adm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamCode {
    @NotBlank
    @Size(min = 20, max = 20, message = "Número de caracteres inválidos")
    private String code;

    public ParamCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
