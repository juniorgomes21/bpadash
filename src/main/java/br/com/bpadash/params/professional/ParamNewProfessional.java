package br.com.bpadash.params.professional;

import javax.validation.constraints.Size;

public class ParamNewProfessional {
    private int id;
    @Size(min = 3, max = 50, message = "testando")
    private String name;
    private String cpf;
    @Size(min = 14, max = 14, message = "testando")
    private String cns;
    @Size(min = 6, max = 6, message = "testando")
    private String cbo;
    private String description;
    private String company;
    private String dateSCNES;

    public ParamNewProfessional() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDateSCNES() {
        return dateSCNES;
    }

    public void setDateSCNES(String dateSCNES) {
        this.dateSCNES = dateSCNES;
    }
}
