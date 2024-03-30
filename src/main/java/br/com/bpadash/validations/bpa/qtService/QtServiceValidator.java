package br.com.bpadash.validations.bpa.qtService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class QtServiceValidator implements ConstraintValidator<ValidQtService, List<Integer>> {
    @Override
    public void initialize(ValidQtService constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Integer> qtServiceList, ConstraintValidatorContext context) {
        if (qtServiceList == null) {
            return true;
        }

        if (qtServiceList.size() != 2) {
            return false;
        }

        int qt = qtServiceList.get(0);
        int qtMax = qtServiceList.get(1);

        return qtMax <= 999999 && qt >= 0 && qt <= qtMax;
    }
}