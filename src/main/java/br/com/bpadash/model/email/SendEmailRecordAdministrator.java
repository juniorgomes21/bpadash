package br.com.bpadash.model.email;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SendEmailRecordAdministrator extends SendEmailRecord {

    @ManyToOne
    private Administrator administrator;

    public SendEmailRecordAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public SendEmailRecordAdministrator(String type, String title, String content, Administrator administrator) {
        super(type, title, content);
        this.administrator = administrator;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}
