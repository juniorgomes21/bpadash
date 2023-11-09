package br.com.bpadash.model;

import javax.persistence.*;

@Entity
public class BpacValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean ident = true;
    private boolean cnes = true;
    private boolean cmp = true;
    private boolean cbo = true;
    private boolean flh = true;
    private boolean seq = true;
    private boolean pa = true;
    private boolean idade = true;
    private boolean qt = true;
    private boolean org = true;

    public BpacValidation() {
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
