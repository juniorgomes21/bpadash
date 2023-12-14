package br.com.bpadash.validations.bpa.race;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceValidator implements ConstraintValidator<RaceValid, String> {

    private boolean updateErrors;

    @Override
    public void initialize(RaceValid constraintAnnotation) {
        updateErrors = constraintAnnotation.updateErrors();
    }

    @Override
    public boolean isValid(String s , ConstraintValidatorContext constraintValidatorContext) {
        if(updateErrors && s == null) {
            return true;
        }

        List<String> list = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "99"));

        return list.contains(s);
    };
}
