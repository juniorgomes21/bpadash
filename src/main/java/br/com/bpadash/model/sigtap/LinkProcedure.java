package br.com.bpadash.model.sigtap;

import br.com.bpadash.utilities.Utilities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LinkProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    private Long fileSizeInBytes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkProcedure")
    private List<Procedure> procedureList = new ArrayList<>();

    public LinkProcedure() {
    }

    public LinkProcedure(String name, Long fileSizeInBytes, String date) {
        this.name = name;
        this.fileSizeInBytes = fileSizeInBytes;
        this.date = Utilities.formatDate(date);
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public List<Procedure> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<Procedure> procedureList) {
        this.procedureList = procedureList;
    }

}
