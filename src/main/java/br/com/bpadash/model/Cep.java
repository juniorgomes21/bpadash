package br.com.bpadash.model;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_cep", columnList = "cep")
})
public class Cep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String es;
    private String cod;
    @ManyToOne
    private LinkCep linkCep;

    public Cep() {
    }

    public Cep(String cep , String es , String cod, LinkCep linkCep) {
        this.cep = cep;
        this.es = es;
        this.cod = cod;
        this.linkCep = linkCep;
    }


    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public LinkCep getLinkCep() {
        return linkCep;
    }

    public void setLinkCep(LinkCep linkCep) {
        this.linkCep = linkCep;
    }
}
