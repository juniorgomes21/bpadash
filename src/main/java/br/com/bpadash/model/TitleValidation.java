package br.com.bpadash.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TitleValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean lin = true;
    private boolean smtVrf = true;
    private boolean flh = true;
    private boolean cgccpf = true;

    public TitleValidation() {
    }

    public Long getId() {
        return id;
    }

    public boolean isLin() {
        return lin;
    }

    public void setLin(boolean lin) {
        this.lin = lin;
    }

    public boolean isFlh() {
        return flh;
    }

    public void setFlh(boolean flh) {
        this.flh = flh;
    }

    public boolean isSmtVrf() {
        return smtVrf;
    }

    public void setSmtVrf(boolean smtVrf) {
        this.smtVrf = smtVrf;
    }

    public boolean isCgccpf() {
        return cgccpf;
    }

    public void setCgccpf(boolean cgccpf) {
        this.cgccpf = cgccpf;
    }

}
