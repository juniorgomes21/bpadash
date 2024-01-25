package br.com.bpadash.validations.user;

import br.com.bpadash.validations.bpa.org.OrgValid;
import br.com.bpadash.validations.bpa.race.RaceValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageUserValidator implements ConstraintValidator<PackageUserValid, String> {

    @Override
    public void initialize(PackageUserValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value , ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = new ArrayList<>(Arrays.asList("bronze", "gold", "platinum"));

        return list.contains(value);
    }
}
