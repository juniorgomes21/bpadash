package br.com.bpadash.dto.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorsFile {
    private String line;
    private List<ErrorValidationDTO> messages = new ArrayList<>();

    public ErrorsFile() {
    }

    public ErrorsFile(String line , List<ErrorValidationDTO> messages) {
        this.line = line;
        this.messages = messages;
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
