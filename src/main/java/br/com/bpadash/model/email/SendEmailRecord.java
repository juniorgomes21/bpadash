package br.com.bpadash.model.email;


import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class SendEmailRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @Column(length = 8000)
    private String msgError;
    private String title;
    private String content;
    private boolean error = false;
    private LocalDateTime creationDate = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));

    public SendEmailRecord() {
    }

    public SendEmailRecord(String type , String title , String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isError() {
        return error;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
