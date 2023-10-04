package br.com.bpadash.validations.bpa.org;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrgValidator implements ConstraintValidator<OrgValid, String> {

    @Override
    public void initialize(OrgValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value , ConstraintValidatorContext constraintValidatorContext) {
        List<String> types = new ArrayList<>(Arrays.asList("BPA", "PNI", "SIE", "SIB", "MIN", "PAC", "SCL", "EXT"));

        return types.contains(value);
    }
}
