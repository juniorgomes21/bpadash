package br.com.bpadash.model.sigtap;

import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.utilities.Utilities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LinkCep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long fileSizeInBytes;
    private LocalDate date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkCep")
    private List<Cep> cepList = new ArrayList<>();

    public LinkCep() {
    }

    public LinkCep(ParamNewCep paramNewCep, Long fileSizeInBytes) {
        this.name = paramNewCep.getName();
        this.fileSizeInBytes = fileSizeInBytes;
        this.date = Utilities.formatDate(paramNewCep.getDate());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Cep> getCepList() {
        return cepList;
    }

    public void setCepList(List<Cep> cepList) {
        this.cepList = cepList;
    }
}
