package br.com.bpadash.model;

import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.utilities.Utilities;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Bpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    private String name;
    private String description;
    private LocalDate date;
    private Long fileSizeInBytes;
    @ManyToOne
    private User user;

    public Bpa() {
    }

    public Bpa(User user, String identifier, ParamNewBpa paramNewBpa) {
        this.identifier = identifier;
        this.name = paramNewBpa.getName();
        this.description = paramNewBpa.getDescription();
        this.date = Utilities.formatDate(paramNewBpa.getDate());
        this.fileSizeInBytes = 0L;
        this.user = user;
    }

    public Long getId() {
        return id;
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

    public Long getFileSizeInBytesInt() {
        return this.fileSizeInBytes;
    }

    public String getFileSizeInBytes() {
        double fileSizeKB = fileSizeInBytes / 1024.0; // Convertendo para KB
        if (fileSizeKB < 1024) {
            return String.format("%.2f KB", fileSizeKB);
        } else {
            double fileSizeMB = fileSizeKB / 1024.0; // Convertendo para MB
            return String.format("%.2f MB", fileSizeMB);
        }
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
