package br.com.bpadash.model.email;

public enum EmailType {
    //ADM
    WELCOME("REGISTER_USER");

    private String type;

    EmailType() {
    }

    EmailType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
