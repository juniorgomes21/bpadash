package br.com.bpadash.validations.bpa.replaceCustom;

import org.springframework.stereotype.Component;

import javax.validation.constraints.AssertTrue;

@Component
public class ReplaceCustomValidation {

    public static boolean isValidForCriterion(String criterion, String valueCriterion) {
        if (!criterion.equals("")) {
            return switch (criterion.toLowerCase()) {
                case "sexo" -> valueCriterion.length() == 1;
                case "fim", "caten", "raca" -> valueCriterion.length() == 2;
                case "org", "idade", "nac", "srv", "clf" -> valueCriterion.length() == 3;
                case "cid", "etnia", "equipearea" -> valueCriterion.length() == 4;
                case "cbo", "cmp", "qt", "ibge" -> valueCriterion.length() == 6;
                case "cnes" -> valueCriterion.length() == 7;
                case "dtaten", "dtnasc", "equipeseq" -> valueCriterion.length() == 8;
                case "pa", "ine" -> valueCriterion.length() == 10;
                case "naut" -> valueCriterion.length() == 13;
                case "cnpj" -> valueCriterion.length() == 14;
                case "cnspac", "cnsmed" -> valueCriterion.length() == 15;
                case "nmpac" -> valueCriterion.length() == 30;

                default -> false;
            };
        }

        return true;
    }
}
