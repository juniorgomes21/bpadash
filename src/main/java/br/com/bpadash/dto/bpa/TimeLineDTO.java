package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.services.user.StorageService;

import java.time.LocalDate;

public class TimeLineDTO {
    private Long id;
    private String name;
    private String description;
    private String fileSize;
    private LocalDate date;
    private String identifier;

    public TimeLineDTO() {
    }

    public TimeLineDTO(Bpa bpa) {
        this.name = bpa.getName();
        this.description = bpa.getDescription();
        this.fileSize = StorageService.formatBytes(bpa.getFileSizeInBytes());
        this.date = bpa.getDate();
        this.identifier = bpa.getIdentifier();
    }

    public TimeLineDTO(LinkProfessionals linkProfessionals) {
        this.id = linkProfessionals.getId();
        this.name = linkProfessionals.getName();
        this.description = "";
        this.fileSize = StorageService.formatBytes(linkProfessionals.getFileSizeInBytes());
        this.date = linkProfessionals.getDate();
        this.identifier = "";
    }

    public TimeLineDTO(LinkFpo linkFpo) {
        this.id = linkFpo.getId();
        this.name = linkFpo.getName();
        this.description = "";
        this.fileSize = StorageService.formatBytes(linkFpo.getFileSizeInBytes());
        this.date = linkFpo.getDate();
        this.identifier = "";
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
