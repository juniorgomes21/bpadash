package br.com.bpadash.params.user;

import javax.validation.constraints.NotBlank;

public class ParamNewUser extends ParamBase{
    @NotBlank
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
