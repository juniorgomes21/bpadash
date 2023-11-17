package br.com.bpadash.model;

import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.params.professional.ParamNewProfessional;
import br.com.bpadash.utilities.Utilities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String cns;
    private String name;
    private String cbo;
    private String description;
    private String company;
    private LocalDate dateScnes;
    private LocalDateTime dateRegister = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));

    public Professional() {
    }

    public Professional(ParamNewProfessional paramNewProfessional, User user) {
        this.cpf = paramNewProfessional.getCpf();
        this.cns = paramNewProfessional.getCns();
        this.name = paramNewProfessional.getName();
        this.cbo = paramNewProfessional.getCbo();
        this.description = paramNewProfessional.getDescription();
        this.company = paramNewProfessional.getCompany();
        this.dateScnes = paramNewProfessional.getDateSCNES().trim().isEmpty() ? LocalDate.now(ZoneId.of(ZoneTime.BR.getBr())) : Utilities.formatDate(paramNewProfessional.getDateSCNES());
    }

    public Long getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getDateScnes() {
        return dateScnes;
    }

    public void setDateScnes(LocalDate dateScnes) {
        this.dateScnes = dateScnes;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }
}
