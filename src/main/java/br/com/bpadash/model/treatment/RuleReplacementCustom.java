package br.com.bpadash.model.treatment;

import br.com.bpadash.params.treatment.ParamTreatmentReplaceCustom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RuleReplacementCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String field;
    @NotNull
    private String newValueField;
    @NotNull
    private String criterionOne;
    @NotNull
    private String valueCriterionOne;
    private String criterionTwo;
    private String valueCriterionTwo;
    private String criterionThree;
    private String valueCriterionThree;
    @NotNull
    private String type;
    private boolean executeBpac = true;
    private boolean executeBpai = true;

    public RuleReplacementCustom() {
    }

    public RuleReplacementCustom(ParamTreatmentReplaceCustom paramTreatmentReplaceCustom) {
        this.field = paramTreatmentReplaceCustom.getField();
        this.newValueField = paramTreatmentReplaceCustom.getNewValueField();
        this.criterionOne = paramTreatmentReplaceCustom.getCriterionOne();
        this.valueCriterionOne = paramTreatmentReplaceCustom.getValueCriterionOne();
        this.criterionTwo = paramTreatmentReplaceCustom.getCriterionTwo() == null ? "" : paramTreatmentReplaceCustom.getCriterionTwo();
        this.valueCriterionTwo = paramTreatmentReplaceCustom.getValueCriterionTwo() == null ? ""  : paramTreatmentReplaceCustom.getValueCriterionTwo();
        this.criterionThree = paramTreatmentReplaceCustom.getCriterionThree() == null ? ""  : paramTreatmentReplaceCustom.getCriterionThree();
        this.valueCriterionThree = paramTreatmentReplaceCustom.getValueCriterionThree() == null ? ""  : paramTreatmentReplaceCustom.getValueCriterionThree();
        this.executeBpac = paramTreatmentReplaceCustom.isExecuteBpac();
        this.executeBpai = paramTreatmentReplaceCustom.isExecuteBpai();
        this.type = paramTreatmentReplaceCustom.getType();
    }

    public Long getId() {
        return id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getNewValueField() {
        return newValueField;
    }

    public void setNewValueField(String newValueField) {
        this.newValueField = newValueField;
    }

    public String getCriterionOne() {
        return criterionOne;
    }

    public void setCriterionOne(String criterionOne) {
        this.criterionOne = criterionOne;
    }

    public String getValueCriterionOne() {
        return valueCriterionOne;
    }

    public void setValueCriterionOne(String valueCriterionOne) {
        this.valueCriterionOne = valueCriterionOne;
    }

    public String getCriterionTwo() {
        return criterionTwo;
    }

    public void setCriterionTwo(String criterionTwo) {
        this.criterionTwo = criterionTwo;
    }

    public String getValueCriterionTwo() {
        return valueCriterionTwo;
    }

    public void setValueCriterionTwo(String valueCriterionTwo) {
        this.valueCriterionTwo = valueCriterionTwo;
    }

    public String getCriterionThree() {
        return criterionThree;
    }

    public void setCriterionThree(String criterionThree) {
        this.criterionThree = criterionThree;
    }

    public String getValueCriterionThree() {
        return valueCriterionThree;
    }

    public void setValueCriterionThree(String valueCriterionThree) {
        this.valueCriterionThree = valueCriterionThree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
