package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.BpacValidation;

public class BpacValidationDTO {
    private boolean cnes;
    private boolean cmp;
    private boolean cbo;
    private boolean pa;
    private boolean idade;
    private boolean qt;
    private boolean org;


    public BpacValidationDTO() {
    }

    public BpacValidationDTO(BpacValidation bpacValidation) {
        this.cnes = bpacValidation.isCnes();
        this.cmp = bpacValidation.isCmp();
        this.cbo = bpacValidation.isCbo();
        this.pa = bpacValidation.isPa();
        this.idade = bpacValidation.isIdade();
        this.qt = bpacValidation.isQt();
        this.org = bpacValidation.isOrg();
    }

    public boolean isCnes() {
        return cnes;
    }

    public void setCnes(boolean cnes) {
        this.cnes = cnes;
    }

    public boolean isCmp() {
        return cmp;
    }

    public void setCmp(boolean cmp) {
        this.cmp = cmp;
    }

    public boolean isCbo() {
        return cbo;
    }

    public void setCbo(boolean cbo) {
        this.cbo = cbo;
    }

    public boolean isPa() {
        return pa;
    }

    public void setPa(boolean pa) {
        this.pa = pa;
    }

    public boolean isIdade() {
        return idade;
    }

    public void setIdade(boolean idade) {
        this.idade = idade;
    }

    public boolean isQt() {
        return qt;
    }

    public void setQt(boolean qt) {
        this.qt = qt;
    }

    public boolean isOrg() {
        return org;
    }

    public void setOrg(boolean org) {
        this.org = org;
    }
}
