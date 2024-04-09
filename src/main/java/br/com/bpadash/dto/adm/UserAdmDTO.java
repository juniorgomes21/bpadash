package br.com.bpadash.dto.adm;

import br.com.bpadash.model.user.User;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.user.StorageService;

public class UserAdmDTO {
    private Long id;
    private String name;
    private String email;
    private String contact;
    private String cnpj;
    private String packageName;
    private String totalStorage;
    private String storageUsed;
    private boolean active;

    public UserAdmDTO() {
    }

    public UserAdmDTO(User user) {
        this.id = user.getId();
        this.name = EnCryptionAESService.decrypt(user.getName());
        this.packageName = EnCryptionAESService.decrypt(user.getPackageNameUser());
        this.email = EnCryptionAESService.decrypt(user.getEmail());
        this.contact = EnCryptionAESService.decrypt(user.getCell());
        this.cnpj = EnCryptionAESService.decrypt(user.getCnpj());
        this.totalStorage = StorageService.formatBytes(user.getStorageTotal());
        this.storageUsed = StorageService.formatBytes(user.getStorageUsed());
        this.active = user.isValid();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTotalStorage() {
        return totalStorage;
    }

    public void setTotalStorage(String totalStorage) {
        this.totalStorage = totalStorage;
    }

    public String getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(String storageUsed) {
        this.storageUsed = storageUsed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
