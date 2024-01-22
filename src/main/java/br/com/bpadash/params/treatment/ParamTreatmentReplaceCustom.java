package br.com.bpadash.params.treatment;

import br.com.bpadash.validations.bpa.replaceCustom.ReplaceCustomValidation;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ParamTreatmentReplaceCustom {
    @NotBlank(message = "FIELD IS BLANK") // TODO fazer validação correta
    private String field;
    @NotBlank(message = "VALUE FIELD IS BLANK")
    private String newValueField;
    @NotBlank(message = "CRITERIO IS BLANK")
    private String criterionOne;
    @NotBlank(message = "VALUE CRITERION IS BLANK")
    private String valueCriterionOne;
    @NotNull
    private String criterionTwo;
    @NotNull
    private String valueCriterionTwo;
    @NotNull
    private String criterionThree;
    @NotNull
    private String valueCriterionThree;
    @NotNull
    private String type;
    @Pattern(regexp = "true|false", message = "O valor deve ser true ou false")
    private String executeBpac;
    @Pattern(regexp = "true|false", message = "O valor deve ser true ou false")
    private String executeBpai;
    @AssertTrue(message = "VALUE CRITERION TWO OR THREE IS BLANK")
    private boolean isValueCriterionTwoOrThreeValid() {
        if ((criterionTwo.equals("") && !criterionTwo.isBlank()) || (criterionThree.equals("") && !criterionThree.isBlank())) {
            return !((valueCriterionTwo == null || valueCriterionTwo.isBlank()) || (valueCriterionThree == null || valueCriterionThree.isBlank()));
        }
        return true;
    }
    @AssertTrue(message = "A REGRA NÃO PODE SER EXECUTADA NOS DOIS ARQUIVOS")
    private boolean isValidExecute() {
        return !executeBpac.equals(executeBpai);
    }
    @AssertTrue(message = "INVALID SIZE VALUE CRITERION FIELD")
    private boolean isValueFieldValid() {
        return ReplaceCustomValidation.isValidForCriterion(field, newValueField);
    }
    @AssertTrue(message = "INVALID SIZE VALUE CRITERION ONE")
    private boolean isValueCriterionOneValid() {
        return ReplaceCustomValidation.isValidForCriterion(criterionOne, valueCriterionOne);
    }
    @AssertTrue(message = "INVALID SIZE VALUE CRITERION TWO")
    private boolean isValueCriterionTwoValid() {
        return ReplaceCustomValidation.isValidForCriterion(criterionTwo, valueCriterionTwo);
    }
    @AssertTrue(message = "INVALID SIZE VALUE CRITERION THREE")
    private boolean isValueCriterionThreeValid() {
        return ReplaceCustomValidation.isValidForCriterion(criterionThree, valueCriterionThree);
    }



    private boolean isLengthValid(String value, int expectedLength) {
        return value != null && value.length() == expectedLength;
    }


    public ParamTreatmentReplaceCustom() {
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
        return executeBpac.equals("true");
    }

    public void setExecuteBpac(boolean executeBpac) {
        this.executeBpac = String.valueOf(executeBpac);
    }

    public boolean isExecuteBpai() {
        return executeBpai.equals("true");
    }

    public void setExecuteBpai(boolean executeBpai) {
        this.executeBpai = String.valueOf(executeBpai);
    }
}
