package br.com.bpadash.dto;

import br.com.bpadash.dto.bpa.BpacValidationDTO;
import br.com.bpadash.dto.bpa.BpaiValidationDTO;
import br.com.bpadash.model.User;
import br.com.bpadash.services.user.StorageService;

public class UserDTO {
    private String name;
    private String email;
    private String cnpj;
    private String cell;
    private boolean valid;
    private boolean changePass;
    private String storageTotal;
    private AddressDTO address;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.cell = user.getCell();
        this.cnpj = user.getCnpj();
        this.valid = user.isValid();
        this.changePass = user.isChangePass();
        this.storageTotal = StorageService.formatBytes(user.getStorageTotal());
        this.address = new AddressDTO(user.getAddress());
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isChangePass() {
        return changePass;
    }

    public void setChangePass(boolean changePass) {
        this.changePass = changePass;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
