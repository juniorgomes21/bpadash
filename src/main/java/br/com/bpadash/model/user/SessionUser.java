package br.com.bpadash.model.user;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SessionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AccountLogged> accountLoggeds = new ArrayList<>();
    @PositiveOrZero
    private int countMaxLogged = 0;

    public SessionUser() {
    }

    public SessionUser(User user, int countMaxLogged) {
        this.user = user;
        this.countMaxLogged = countMaxLogged;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AccountLogged> getAccountLoggeds() {
        return accountLoggeds;
    }

    public void setAccountLoggeds(List<AccountLogged> accountLoggeds) {
        this.accountLoggeds = accountLoggeds;
    }

    public int getCountMaxLogged() {
        return countMaxLogged;
    }

    public void setCountMaxLogged(int countMaxLogged) {
        this.countMaxLogged = countMaxLogged;
    }

}
