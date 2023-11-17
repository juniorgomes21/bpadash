package br.com.bpadash.errorValidation;

public class ErrorResponseDTO {
    private String msgError = "Ops, algo deu errado!";

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String msgError) {
        this.msgError = msgError;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }
}
