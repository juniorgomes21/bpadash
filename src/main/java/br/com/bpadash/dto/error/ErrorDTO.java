package br.com.bpadash.dto.error;

public class ErrorDTO {
    private String message = "Ops, algo deu errado!";

    public ErrorDTO() {
    }

    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
