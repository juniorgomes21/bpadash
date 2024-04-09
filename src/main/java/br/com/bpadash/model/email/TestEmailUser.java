package br.com.bpadash.model.email;

import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.params.adm.ParamTestEmailUser;
import br.com.bpadash.services.cryptography.EnCryptionAESService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class TestEmailUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
    private boolean valid = false;
    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));

    public TestEmailUser() {
    }

    public TestEmailUser(ParamTestEmailUser paramTestEmailUser) {
        this.email = EnCryptionAESService.encrypt(paramTestEmailUser.getEmail());
        this.code = EnCryptionAESService.encrypt(paramTestEmailUser.getCode());
    }

    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
