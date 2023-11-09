package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.Bpa;

import java.time.LocalDate;

public class TimeLineUserDTO {
    private String name;
    private String description;
    private String fileSize;
    private LocalDate date;
    private String identifier;

    public TimeLineUserDTO() {
    }

    public TimeLineUserDTO(Bpa bpa) {
        this.name = bpa.getName();
        this.description = bpa.getDescription();
        this.fileSize = bpa.getFileSizeInBytes();
        this.date = bpa.getDate();
        this.identifier = bpa.getIdentifier();
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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
