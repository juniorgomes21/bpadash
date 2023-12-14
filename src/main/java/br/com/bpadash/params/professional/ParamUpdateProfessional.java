package br.com.bpadash.params.professional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ParamUpdateProfessional {
    @Positive
    private Long id;
    @NotNull
    private String profId;
    @NotNull
    private String cpf;
    @NotNull
    private String name;
    @NotNull
    private String logradouro;
    @NotNull
    private String number;
    @NotNull
    private String complement;
    @NotNull
    private String bairrodist;
    @NotNull
    private String codCep;
    @NotNull
    private String codCns;
    @NotNull
    private String telephone;
    @NotNull
    private String codCbo;

    public ParamUpdateProfessional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
