package br.com.bpadash.dto.user;

import br.com.bpadash.model.user.Employee;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class AccountLoggedDTO {
    private String name;

    public AccountLoggedDTO() {
    }

    public AccountLoggedDTO(Employee employee) {
        this.name = employee.getName();
    }

    public AccountLoggedDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
