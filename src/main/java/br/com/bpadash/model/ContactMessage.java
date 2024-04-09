package br.com.bpadash.model;

import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.params.ContactMessageParam;
import br.com.bpadash.services.cryptography.EnCryptionAESService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class ContactMessage {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String cell;
    private String packageName;
    private String message;
    private boolean conclude = false;
    private LocalDateTime date = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));


    public ContactMessage() {
    }

    public ContactMessage(ContactMessageParam contactMessageParam) {
        this.name = EnCryptionAESService.encrypt(contactMessageParam.getName());
        this.email = EnCryptionAESService.encrypt(contactMessageParam.getEmail());
        this.cell = contactMessageParam.getCell().length() != 11 ? "" : EnCryptionAESService.encrypt(contactMessageParam.getCell());
        this.packageName = EnCryptionAESService.encrypt(contactMessageParam.getPackageName());
        this.message = EnCryptionAESService.encrypt(contactMessageParam.getMessage());
    }

    public Long getId() {
        return id;
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

    public boolean isConclude() {
        return conclude;
    }

    public void setConclude(boolean conclude) {
        this.conclude = conclude;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
