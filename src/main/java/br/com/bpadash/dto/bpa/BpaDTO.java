package br.com.bpadash.dto.bpa;


import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.services.user.StorageService;

import java.time.LocalDate;

public class BpaDTO {
    private String identifier;
    private String name;
    private String description;
    private LocalDate date;
    private String sizeFile;

    public BpaDTO() {
    }

    public BpaDTO(Bpa bpa) {
        this.name = bpa.getName();
        this.date = bpa.getDate();
        this.identifier = bpa.getIdentifier();
        this.description = bpa.getDescription();
        this.sizeFile = StorageService.formatBytes(bpa.getFileSizeInBytes());
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String sizeFile) {
        this.sizeFile = sizeFile;
    }
}
