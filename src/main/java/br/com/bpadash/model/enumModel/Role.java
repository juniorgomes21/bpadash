package br.com.bpadash.model.enumModel;

public enum Role {

    USER("USER"),
    ADMINISTRATOR("ADMINISTRATOR");

    private String nome;

    Role(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
