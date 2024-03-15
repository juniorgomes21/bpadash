package br.com.bpadash.dto.user;

import br.com.bpadash.model.user.Employee;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;

import javax.persistence.OneToOne;
import java.time.LocalDateTime;

public class EmployeeDTO {
    private String id;
    private String key;
    private String name;
    private String email;
    private boolean isMaster;
    private LocalDateTime lastLogin;
    private PermissionsEmployeeDTO permissionsEmployeeDTO;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee, String key) {
        this.id = EncryptionService.encryptId(employee.getId());
        this.key = key;
        this.name = employee.getName();
        this.email = EnCryptionAESService.decrypt(employee.getEmail());
        this.isMaster = employee.isMaster();
        this.lastLogin = employee.getLastLogin();
        this.permissionsEmployeeDTO = new PermissionsEmployeeDTO(employee.getPermissions());
    }

    public EmployeeDTO(Employee employee) {
        this.id = EncryptionService.encryptId(employee.getId());
        this.name = employee.getName();
        this.email = EnCryptionAESService.decrypt(employee.getEmail());
        this.isMaster = employee.isMaster();
        this.lastLogin = employee.getLastLogin();
        this.permissionsEmployeeDTO = new PermissionsEmployeeDTO(employee.getPermissions());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public PermissionsEmployeeDTO getPermissionsEmployeeDTO() {
        return permissionsEmployeeDTO;
    }

    public void setPermissionsEmployeeDTO(PermissionsEmployeeDTO permissionsEmployeeDTO) {
        this.permissionsEmployeeDTO = permissionsEmployeeDTO;
    }
}
