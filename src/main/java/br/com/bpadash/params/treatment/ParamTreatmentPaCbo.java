package br.com.bpadash.params.treatment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamTreatmentPaCbo {

    @NotBlank
    @Size(min = 10, max = 10, message = "O campo tem que ter 10 digítos")
    private String pa;
    @NotBlank
    @Size(min = 6, max = 6, message = "O campo tem que ter 6 digítos")
    private String cboCurrent;
    @NotBlank
    @Size(min = 6, max = 6, message = "O campo tem que ter 6 digítos")
    private String cboNew;


    public ParamTreatmentPaCbo() {
    }


    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getCboCurrent() {
        return cboCurrent;
    }

    public void setCboCurrent(String cboCurrent) {
        this.cboCurrent = cboCurrent;
    }

    public String getCboNew() {
        return cboNew;
    }

    public void setCboNew(String cboNew) {
        this.cboNew = cboNew;
    }
}
