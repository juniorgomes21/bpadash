package br.com.bpadash.dto;

import br.com.bpadash.model.user.User;
import br.com.bpadash.services.user.StorageService;

public class StorageDTO {
    private String storageTotal;
    private String storageUsed;
    private String storagePorcent;

    public StorageDTO() {
    }

    public StorageDTO(User user) {
        this.storageTotal = StorageService.formatBytes(user.getStorageTotal());
        this.storageUsed = StorageService.formatBytes(user.getStorageUsed());
        this.storagePorcent = StorageService.porcent(user.getStorageTotal(), user.getStorageUsed());
    }

    public String getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(String storageTotal) {
        this.storageTotal = storageTotal;
    }

    public String getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(String storageUsed) {
        this.storageUsed = storageUsed;
    }

    public String getStoragePorcent() {
        return storagePorcent;
    }

    public void setStoragePorcent(String storagePorcent) {
        this.storagePorcent = storagePorcent;
    }
}
