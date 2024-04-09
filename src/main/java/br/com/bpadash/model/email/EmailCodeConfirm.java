package br.com.bpadash.model.email;

import br.com.bpadash.model.enumModel.ZoneTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class EmailCodeConfirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 900)
    private String token;
    private String code;
    private boolean isValid = true;
    private LocalDateTime dateCreate = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    private LocalDateTime dateExpires = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr())).plusMinutes(2L);


    public EmailCodeConfirm() {
    }

    public EmailCodeConfirm(String code, String token) {
        this.code = code;
        this.token = token;
    }

    public EmailCodeConfirm(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(LocalDateTime dateExpires) {
        this.dateExpires = dateExpires;
    }
}
