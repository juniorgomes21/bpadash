package br.com.bpadash.model.bpa;

import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamNewBpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Bpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    private String name;
    private String description;
    private LocalDate date;
    private Long fileSizeInBytes;
    @ManyToOne
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private ManagerBpa managerBpa = new ManagerBpa();
//    @OneToOne(cascade = CascadeType.ALL)
//    private ManagerYear managerYear = new ManagerYear();

    public Bpa() {
    }

    public Bpa(User user, String identifier, ParamNewBpa paramNewBpa) {
        this.identifier = identifier;
        this.name = paramNewBpa.getName();
        this.description = paramNewBpa.getDescription();
        this.fileSizeInBytes = 0L;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ManagerBpa getManagerBpa() {
        return managerBpa;
    }

    public void setManagerBpa(ManagerBpa managerBpa) {
        this.managerBpa = managerBpa;
    }

//    public ManagerYear getManagerYear() {
//        return managerYear;
//    }
//
//    public void setManagerYear(ManagerYear managerYear) {
//        this.managerYear = managerYear;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bpa bpa)) return false;
        return Objects.equals(getId() , bpa.getId()) && Objects.equals(getIdentifier() , bpa.getIdentifier()) && Objects.equals(getDate() , bpa.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId() , getIdentifier() , getDate());
    }
}
