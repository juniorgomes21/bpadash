package br.com.bpadash.params;

import javax.validation.constraints.NotBlank;

public class ParamNewUser {
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String email;
    @NotBlank
    private String cell;
    @NotBlank
    private String cep;
    @NotBlank
    private String password;
    @NotBlank
    private String password2;

    public ParamNewUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
