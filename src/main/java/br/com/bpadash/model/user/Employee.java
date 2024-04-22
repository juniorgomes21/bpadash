package br.com.bpadash.model.user;

import br.com.bpadash.params.user.ParamCreateEmployee;
import br.com.bpadash.services.cryptography.EnCryptionAESService;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isMaster = false;
    private LocalDateTime lastLogin;
    @OneToOne(cascade = CascadeType.ALL)
    private PermissionsEmployee permissions = new PermissionsEmployee();
    @ManyToOne
    private User user;

    public Employee() {
    }

    public Employee(ParamCreateEmployee paramCreateEmployee, User user) {
        this.name = EnCryptionAESService.encrypt(paramCreateEmployee.getName());
        this.email = EnCryptionAESService.encrypt(paramCreateEmployee.getEmail());
        this.password = EnCryptionAESService.hashString(paramCreateEmployee.getPassword());
        this.user = user;
    }

    public Employee(String name, String email, String password, boolean isMaster, User user) {
        this.name = EnCryptionAESService.encrypt(name);
        this.password = EnCryptionAESService.hashString(password);
        this.email = email;
        this.isMaster = isMaster;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return EnCryptionAESService.decrypt(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public PermissionsEmployee getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsEmployee permissions) {
        this.permissions = permissions;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(getId() , employee.getId()) && Objects.equals(getName() , employee.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId() , getName());
    }
}
