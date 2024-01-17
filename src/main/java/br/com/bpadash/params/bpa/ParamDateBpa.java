package br.com.bpadash.params.bpa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamDateBpa {
    @NotBlank
    @Size(min = 10, max = 10, message = "Data inválida")
    private String dateBpa;

    public ParamDateBpa() {
    }

    public String getDateBpa() {
        return dateBpa;
    }

    public void setDateBpa(String dateBpa) {
        this.dateBpa = dateBpa;
    }
}
