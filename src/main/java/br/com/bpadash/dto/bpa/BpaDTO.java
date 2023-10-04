package br.com.bpadash.dto.bpa;


import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.TitleBpa;

import java.util.List;

public class BpaDTO {
    private String identifier;
    private String name;
    private String description;
    private String sizeFile;

    public BpaDTO() {
    }

    public BpaDTO(Bpa bpa) {
        this.identifier = bpa.getIdentifier();
        this.name = bpa.getName();
        this.description = bpa.getDescription();
        this.sizeFile = bpa.getFileSizeInBytes();
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

    public String getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String sizeFile) {
        this.sizeFile = sizeFile;
    }
}
