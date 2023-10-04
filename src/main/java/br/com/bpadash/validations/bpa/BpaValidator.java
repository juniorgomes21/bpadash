package br.com.bpadash.validations.bpa;

import br.com.bpadash.validations.bpa.BpaValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BpaValidator implements ConstraintValidator<BpaValid, String> {

    private int size;
    private boolean alfa;

    @Override
    public void initialize(BpaValid annotation) {
        size = annotation.size();
        alfa = annotation.alfa();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        if(alfa) {
            return value.length() == size;
        }

        return value.length() == size && value.matches("^[0-9]+$");
    }
}
