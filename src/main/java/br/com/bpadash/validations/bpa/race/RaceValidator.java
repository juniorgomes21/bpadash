package br.com.bpadash.validations.bpa.race;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceValidator implements ConstraintValidator<RaceValid, String> {

    @Override
    public void initialize(RaceValid constraintAnnotation) {}

    @Override
    public boolean isValid(String s , ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "99"));

        return list.contains(s);
    };
}
