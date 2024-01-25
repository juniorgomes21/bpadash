package br.com.bpadash.model.sigtap;

import br.com.bpadash.model.user.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.utilities.Utilities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LinkFpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long fileSizeInBytes;
    @Column(unique = true)
    private LocalDate date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkFpo")
    private List<Fpo> fpoList = new ArrayList<>();
    @ManyToOne
    private User user;


    public LinkFpo() {
    }

    public LinkFpo(ParamNewFpo paramNewFpo, Long fileSizeInBytes, User user) {
        this.name = paramNewFpo.getName();
        this.fileSizeInBytes = fileSizeInBytes;
        this.date = Utilities.formatDate(paramNewFpo.getDate());
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

    public List<Fpo> getFpoList() {
        return fpoList;
    }

    public void setFpoList(List<Fpo> fpoList) {
        this.fpoList = fpoList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
