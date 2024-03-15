package br.com.bpadash.model.user;

import br.com.bpadash.model.enumModel.ZoneTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String type;
    private int linesModified;
    private LocalDate dateFile;
    private LocalDateTime date = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    @ManyToOne
    private Employee employee;
    @OneToOne
    private User user;

    public StockHistory() {
    }

    public StockHistory(String action, String type, LocalDate dateFile, int linesModified, User user, Employee employee) {
        this.action = action;
        this.type = type;
        this.linesModified = linesModified;
        this.dateFile = dateFile;
        this.user = user;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLinesModified() {
        return linesModified;
    }

    public void setLinesModified(int linesModified) {
        this.linesModified = linesModified;
    }

    public LocalDate getDateFile() {
        return dateFile;
    }

    public void setDateFile(LocalDate dateFile) {
        this.dateFile = dateFile;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
