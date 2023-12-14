package br.com.bpadash.model.treatment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RuleTreatmentPaCbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pa;
    private String cboCurrent;
    private String cboNew;

    public Long getId() {
        return id;
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
