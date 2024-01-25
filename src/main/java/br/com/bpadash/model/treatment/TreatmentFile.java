package br.com.bpadash.model.treatment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TreatmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int count;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RuleTreatmentPa> ruleTreatmentPaList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<RuleTreatmentPaCbo> ruleTreatmentPaCboList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<RuleTreatmentPaDelete> ruleTreatmentPaDeleteList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<RuleReplacementCustom> ruleReplacementCustoms = new ArrayList<>();

    public TreatmentFile() {
    }

    public TreatmentFile(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public List<RuleTreatmentPaDelete> getRuleTreatmentPaDeleteList() {
        return ruleTreatmentPaDeleteList;
    }

    public void setRuleTreatmentPaDeleteList(List<RuleTreatmentPaDelete> ruleTreatmentPaDeleteList) {
        this.ruleTreatmentPaDeleteList = ruleTreatmentPaDeleteList;
    }

    public List<RuleReplacementCustom> getRuleReplacementCustoms() {
        return ruleReplacementCustoms;
    }

    public void setRuleReplacementCustoms(List<RuleReplacementCustom> ruleReplacementCustoms) {
        this.ruleReplacementCustoms = ruleReplacementCustoms;
    }
}
