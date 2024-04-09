package br.com.bpadash.model.adm;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.email.EmailCodeConfirm;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class EmailCodeAdministrator extends EmailCodeConfirm {

    @ManyToOne
    private Administrator administrator;

    public EmailCodeAdministrator() {
    }

    public EmailCodeAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public EmailCodeAdministrator(String code, String token, Administrator administrator1) {
        super(code, token);
        this.administrator = administrator1;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}
