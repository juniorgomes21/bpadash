package br.com.bpadash.params.professional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ParamUpdateProfessional {
    @NotBlank
    @Size(min = 15, max = 15)
    private String codCns;
    @NotBlank
    @Size(min = 6, max = 6)
    private String codCbo;
    @NotNull
    @Size(max = 30)
    private String logradouro;
    @NotNull
    @Size(max = 6)
    private String number;
    @NotNull
    @Size(max = 30)
    private String complement;
    @NotNull
    @Size(max = 30)
    private String bairrodist;
    @NotNull
    @Size(max = 8)
    private String codCep;
    @NotNull
    @Size(max = 11)
    private String telephone;


    public ParamUpdateProfessional() {
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getBairrodist() {
        return bairrodist;
    }

    public void setBairrodist(String bairrodist) {
        this.bairrodist = bairrodist;
    }

    public String getCodCep() {
        return codCep;
    }

    public void setCodCep(String codCep) {
        this.codCep = codCep;
    }

    public String getCodCns() {
        return codCns;
    }

    public void setCodCns(String codCns) {
        this.codCns = codCns;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCodCbo() {
        return codCbo;
    }

    public void setCodCbo(String codCbo) {
        this.codCbo = codCbo;
    }
}
