package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.BpacValidation;

public class BpacValidationDTO {

    private boolean ident;
    private boolean cnes;
    private boolean cmp;
    private boolean cbo;
    private boolean flh;
    private boolean seq;
    private boolean pa;
    private boolean idade;
    private boolean qt;
    private boolean org;


    public BpacValidationDTO() {
    }

    public BpacValidationDTO(BpacValidation bpacValidation) {
        this.ident = bpacValidation.isIdent();
        this.cnes = bpacValidation.isCnes();
        this.cmp = bpacValidation.isCmp();
        this.cbo = bpacValidation.isCbo();
        this.flh = bpacValidation.isFlh();
        this.seq = bpacValidation.isSeq();
        this.pa = bpacValidation.isPa();
        this.idade = bpacValidation.isIdade();
        this.qt = bpacValidation.isQt();
        this.org = bpacValidation.isOrg();
    }

    public boolean isIdent() {
        return ident;
    }

    public void setIdent(boolean ident) {
        this.ident = ident;
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

    public boolean isFlh() {
        return flh;
    }

    public void setFlh(boolean flh) {
        this.flh = flh;
    }

    public boolean isSeq() {
        return seq;
    }

    public void setSeq(boolean seq) {
        this.seq = seq;
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
