package br.com.bpadash.model.treatment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RuleTreatmentPaDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pa;
    private boolean executeBpac = true;
    private boolean executeBpai = true;


    public RuleTreatmentPaDelete() {
    }

    public RuleTreatmentPaDelete(String pa) {
        this.pa = pa;
    }

    public Long getId() {
        return id;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public boolean isExecuteBpac() {
        return executeBpac;
    }

    public void setExecuteBpac(boolean executeBpac) {
        this.executeBpac = executeBpac;
    }

    public boolean isExecuteBpai() {
        return executeBpai;
    }

    public void setExecuteBpai(boolean executeBpai) {
        this.executeBpai = executeBpai;
    }
}
