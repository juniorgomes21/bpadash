package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleReplacementCustom;

import javax.validation.constraints.NotNull;

public class RuleReplaceCustomDTO {
    private Long id;
    private String field;
    private String newValueField;
    private String criterionOne;
    private String valueCriterionOne;
    private String criterionTwo;
    private String valueCriterionTwo;
    private String criterionThree;
    private String valueCriterionThree;
    private String type;
    private boolean executeBpac;
    private boolean executeBpai;

    public RuleReplaceCustomDTO() {
    }

    public RuleReplaceCustomDTO(RuleReplacementCustom ruleReplacementCustom) {
        this.id = ruleReplacementCustom.getId();
        this.field = ruleReplacementCustom.getField();
        this.newValueField = ruleReplacementCustom.getNewValueField();
        this.criterionOne = ruleReplacementCustom.getCriterionOne();
        this.valueCriterionOne = ruleReplacementCustom.getValueCriterionOne();
        this.criterionTwo = ruleReplacementCustom.getCriterionTwo();
        this.valueCriterionTwo = ruleReplacementCustom.getValueCriterionTwo();
        this.criterionThree = ruleReplacementCustom.getCriterionThree();
        this.valueCriterionThree = ruleReplacementCustom.getValueCriterionThree();
        this.type = ruleReplacementCustom.getType();
        this.executeBpac = ruleReplacementCustom.isExecuteBpac();
        this.executeBpai = ruleReplacementCustom.isExecuteBpai();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
