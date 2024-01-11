package br.com.bpadash.params.adm;

import br.com.bpadash.params.user.ParamBase;
import br.com.bpadash.params.user.ParamNewUser;

import javax.validation.constraints.NotBlank;

public class ParamNewAdm extends ParamBase {
    @NotBlank
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
