package br.com.bpadash.errorValidation;

import java.util.ArrayList;
import java.util.List;

public class ErrorInLine {
    private String field;
    private String value;
    private String errorCode;
    private List<String> message = new ArrayList<>();

    public ErrorInLine() {
    }

    public ErrorInLine(String field) {
        this.field = field;
    }

    public ErrorInLine(String field , List<String> message) {
        this.field = field;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
