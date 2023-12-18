package br.com.bpadash.params.treatment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamTreatmentPaDelete {
    @NotBlank
    @Size(min = 10, max = 10, message = "O campo tem que ter 10 digítos")
    private String pa;

    public ParamTreatmentPaDelete() {
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }
}
