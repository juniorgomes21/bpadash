package br.com.bpadash.dto;

import br.com.bpadash.dto.bpa.BpacValidationDTO;
import br.com.bpadash.dto.bpa.BpaiValidationDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.BpacValidation;
import br.com.bpadash.model.BpaiValidation;
import br.com.bpadash.model.User;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.services.user.StorageService;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String name;
    private String email;
    private String cell;
    private boolean valid;
    private String storageTotal;
    private BpacValidationDTO bpacValidation;
    private BpaiValidationDTO bpaiValidation;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.cell = user.getCell();
        this.valid = user.isValid();
        this.storageTotal = StorageService.formatBytes(user.getStorageTotal());
        this.bpacValidation = new BpacValidationDTO(user.getBpacValidation());
        this.bpaiValidation = new BpaiValidationDTO(user.getBpaiValidation());
    }

    public String getName() {
        return name;
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

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(String storageTotal) {
        this.storageTotal = storageTotal;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public BpacValidationDTO getBpacValidation() {
        return bpacValidation;
    }

    public void setBpacValidation(BpacValidationDTO bpacValidation) {
        this.bpacValidation = bpacValidation;
    }

    public BpaiValidationDTO getBpaiValidation() {
        return bpaiValidation;
    }

    public void setBpaiValidation(BpaiValidationDTO bpaiValidation) {
        this.bpaiValidation = bpaiValidation;
    }
}
