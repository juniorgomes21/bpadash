package br.com.bpadash.model.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccountLogged {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long indentifier;
    private String keyEmployee;
    @ManyToOne
    private Employee employee;
    private LocalDateTime date;

    public AccountLogged() {
    }

    public AccountLogged(Employee employee, Long id, LocalDateTime date, String keyEmployee) {
        this.indentifier = id;
        this.keyEmployee = keyEmployee;
        this.employee = employee;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getIndentifier() {
        return indentifier;
    }

    public void setIndentifier(Long indentifier) {
        this.indentifier = indentifier;
    }

    public String getKeyEmployee() {
        return keyEmployee;
    }

    public void setKeyEmployee(String keyEmployee) {
        this.keyEmployee = keyEmployee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
