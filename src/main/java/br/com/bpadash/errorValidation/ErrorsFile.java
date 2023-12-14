package br.com.bpadash.errorValidation;

import java.util.ArrayList;
import java.util.List;

public class ErrorsFile {
    private String errorType;
    private String line;
    private List<ErrorValidationDTO> messages = new ArrayList<>();

    public ErrorsFile() {
    }

    public ErrorsFile(String line , List<ErrorValidationDTO> messages) {
        this.line = line;
        this.messages = messages;
    }

    public ErrorsFile(String errorType , String line) {
        this.errorType = errorType;
        this.line = line;
    }

    public ErrorsFile(String errorType) {
        this.errorType = errorType;
        this.line = "0";
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<ErrorValidationDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ErrorValidationDTO> messages) {
        this.messages = messages;
    }
}
