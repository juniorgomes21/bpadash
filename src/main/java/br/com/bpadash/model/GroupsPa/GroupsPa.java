package br.com.bpadash.model.GroupsPa;

import br.com.bpadash.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupsPa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0)
    @Max(value = 10)
    private int count = 9;
    @ElementCollection
    private List<String> paList = new ArrayList<>();
    @ManyToOne
    private User user;

    public GroupsPa() {
    }

    public Long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getPaList() {
        return paList;
    }

    public void setPaList(List<String> paList) {
        this.paList = paList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
