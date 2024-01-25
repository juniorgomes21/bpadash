package br.com.bpadash.model.sigtap;

import br.com.bpadash.model.user.User;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.utilities.Utilities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LinkProfessionals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    private Long fileSizeInBytes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkProfessionals")
    private List<ProfessionalComplete> professionalCompleteList = new ArrayList<>();
    @ManyToOne
    private User user;

    public LinkProfessionals() {
    }

    public LinkProfessionals(ParamNewProfessionals paramNewProfessionals, Long fileSizeInBytes, User user) {
        this.name = paramNewProfessionals.getName();
        this.fileSizeInBytes = fileSizeInBytes;
        this.date = Utilities.formatDate(paramNewProfessionals.getDate());
        this.user = user;
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

    public List<ProfessionalComplete> getProfessionalCompleteList() {
        return professionalCompleteList;
    }

    public void setProfessionalCompleteList(List<ProfessionalComplete> professionalCompleteList) {
        this.professionalCompleteList = professionalCompleteList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
