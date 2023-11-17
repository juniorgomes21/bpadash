package br.com.bpadash.errorValidation;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalErrorValidation {
    private String errorType;
    private List<Integer> numbers = new ArrayList<>();

    public ProfessionalErrorValidation() {
    }

    public ProfessionalErrorValidation(String errorType) {
        this.errorType = errorType;
    }

    public ProfessionalErrorValidation(String errorType, List<Integer> numbers) {
        this.errorType = errorType;
        this.numbers = numbers;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
