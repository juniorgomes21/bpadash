package br.com.bpadash.model.enumModel;

public enum Role {

    USER("USER"),
    TOKEN_TEMP("TOKEN_TEMP"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
