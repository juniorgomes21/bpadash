package br.com.bpadash.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CodLograd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cod;
    private String keyCod;


    public CodLograd() {
    }

    public CodLograd(String cod , String keyCod) {
        this.cod = cod;
        this.keyCod = keyCod;
    }

    public Long getId() {
        return id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getKeyCod() {
        return keyCod;
    }

    public void setKeyCod(String keyCod) {
        this.keyCod = keyCod;
    }
}
