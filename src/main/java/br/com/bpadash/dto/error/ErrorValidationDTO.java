package br.com.bpadash.dto.error;

public class ErrorValidationDTO {
    private String field;
    private String message;

    public ErrorValidationDTO(String field , String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}