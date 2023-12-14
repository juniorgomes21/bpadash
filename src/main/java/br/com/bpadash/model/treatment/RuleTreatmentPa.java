package br.com.bpadash.model.treatment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RuleTreatmentPa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paCurrent;
    private String paNew;

    public RuleTreatmentPa(String paCurrent , String paNew) {
        this.paCurrent = paCurrent;
        this.paNew = paNew;
    }

    public Long getId() {
        return id;
    }

    public String getPaCurrent() {
        return paCurrent;
    }

    public void setPaCurrent(String paCurrent) {
        this.paCurrent = paCurrent;
    }

    public String getPaNew() {
        return paNew;
    }

    public void setPaNew(String paNew) {
        this.paNew = paNew;
    }
}
