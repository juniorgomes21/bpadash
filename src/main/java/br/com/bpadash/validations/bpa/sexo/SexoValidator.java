package br.com.bpadash.validations.bpa.sexo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SexoValidator implements ConstraintValidator<SexoValid, String> {

    @Override
    public void initialize(SexoValid sexoValid) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = new ArrayList<>(Arrays.asList("M", "F"));

        return list.contains(s.toUpperCase());
    }
}
