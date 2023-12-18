package br.com.bpadash.params.treatment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamTreatmentPa {
    @NotBlank
    @Size(min = 10, max = 10, message = "O campo tem que ter 10 digítos")
    private String paCurrent;
    @NotBlank
    @Size(min = 10, max = 10, message = "O campo tem que ter 10 digítos")
    private String newPa;

    public ParamTreatmentPa() {
    }

    public String getPaCurrent() {
        return paCurrent;
    }

    public void setPaCurrent(String paCurrent) {
        this.paCurrent = paCurrent;
    }

    public String getNewPa() {
        return newPa;
    }

    public void setNewPa(String newPa) {
        this.newPa = newPa;
    }
}
