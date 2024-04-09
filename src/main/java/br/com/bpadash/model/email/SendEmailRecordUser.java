package br.com.bpadash.model.email;

import br.com.bpadash.model.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SendEmailRecordUser extends SendEmailRecord {
    @ManyToOne
    private User user;

    public SendEmailRecordUser(User user) {
        this.user = user;
    }

    public SendEmailRecordUser(String type, String title, String content, User user1) {
        super(type, title, content);
        this.user = user1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
