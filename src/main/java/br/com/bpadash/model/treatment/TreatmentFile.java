package br.com.bpadash.model.treatment;

import br.com.bpadash.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TreatmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<RuleTreatmentPa> ruleTreatmentPaList = new ArrayList<>();
    @OneToMany
    private List<RuleTreatmentPaCbo> ruleTreatmentPaCboList = new ArrayList<>();

    public TreatmentFile() {
    }


    public Long getId() {
        return id;
    }

    public List<RuleTreatmentPa> getRuleTreatmentPaList() {
        return ruleTreatmentPaList;
    }

    public void setRuleTreatmentPaList(List<RuleTreatmentPa> ruleTreatmentPaList) {
        this.ruleTreatmentPaList = ruleTreatmentPaList;
    }

    public List<RuleTreatmentPaCbo> getRuleTreatmentPaCboList() {
        return ruleTreatmentPaCboList;
    }

    public void setRuleTreatmentPaCboList(List<RuleTreatmentPaCbo> ruleTreatmentPaCboList) {
        this.ruleTreatmentPaCboList = ruleTreatmentPaCboList;
    }
}
