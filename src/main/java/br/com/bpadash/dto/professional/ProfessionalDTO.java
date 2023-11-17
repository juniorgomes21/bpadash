package br.com.bpadash.dto.professional;

public class ProfessionalDTO {
    private String name;
    private String cpf;
    private String cns;
    private String cbo;
    private String description;
    private String company;
    private String dateSCNES;

    public ProfessionalDTO() {
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
