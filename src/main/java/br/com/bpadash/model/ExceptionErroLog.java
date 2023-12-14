package br.com.bpadash.model;

import br.com.bpadash.model.enumModel.ZoneTime;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class ExceptionErroLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    @Column(columnDefinition = "TEXT")
    private String stackTrace;
    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));

    public ExceptionErroLog(String mensagem , String stackTrace) {
        this.mensagem = mensagem;
        this.stackTrace = stackTrace;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
