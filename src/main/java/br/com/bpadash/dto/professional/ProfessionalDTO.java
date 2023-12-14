package br.com.bpadash.dto.professional;

import br.com.bpadash.model.DadosVinc;
import br.com.bpadash.model.ProfessionalComplete;

public class ProfessionalDTO {
    private Long id;
    private String profId;
    private String cpf;
    private String name;
    private String logradouro;
    private String number;
    private String complement;
    private String bairrodist;
    private String codCep;
    private String codCns;
    private String telephone;
    private String codCbo;


    public ProfessionalDTO() {
    }

    public ProfessionalDTO(ProfessionalComplete professionalComplete) {
        this.id = professionalComplete.getId();
        this.profId = professionalComplete.getProfId();
        this.name = professionalComplete.getName(); // ok
        this.cpf = professionalComplete.getCpf(); // ok
        this.codCns = professionalComplete.getCodCns();
        this.codCbo = professionalComplete.getDadosVinc().getCodCbo();
        this.logradouro = professionalComplete.getLogradouro();
        this.number = professionalComplete.getNumber();
        this.complement = professionalComplete.getComplement();
        this.bairrodist = professionalComplete.getBairrodist();
        this.codCep = professionalComplete.getCodCep();
        this.telephone = professionalComplete.getTelephone();
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
