package br.com.bpadash.validations.bpa.sexo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SexoValidator implements ConstraintValidator<SexoValid, String> {

    private boolean updateErrors;

    @Override
    public void initialize(SexoValid sexoValid) {
        updateErrors = sexoValid.updateErrors();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(updateErrors && s == null) {
            return true;
        }

        List<String> list = new ArrayList<>(Arrays.asList("M", "F"));

        return list.contains(s.toUpperCase());
    }
}
