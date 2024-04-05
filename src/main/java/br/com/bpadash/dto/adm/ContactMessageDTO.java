package br.com.bpadash.dto.adm;

import br.com.bpadash.model.ContactMessage;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.services.cryptography.EnCryptionAESService;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ContactMessageDTO {
    private Long id;
    private String name;
    private String email;
    private String packageName;
    private String message;
    private LocalDateTime date;

    public ContactMessageDTO() {
    }

    public ContactMessageDTO(ContactMessage contactMessage) {
        this.id = contactMessage.getId();
        this.name = EnCryptionAESService.decrypt(contactMessage.getName());
        this.email = EnCryptionAESService.decrypt(contactMessage.getEmail());
        this.packageName = this.formatPackageName(EnCryptionAESService.decrypt(contactMessage.getPackageName()));
        this.message = EnCryptionAESService.decrypt(contactMessage.getMessage());
        this.date = contactMessage.getDate();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private String formatPackageName(String packageName) {

        return packageName.equals("bronze") ? "Bronze" : packageName.equals("gold") ? "Ouro" : "Platinha";
    }
}
