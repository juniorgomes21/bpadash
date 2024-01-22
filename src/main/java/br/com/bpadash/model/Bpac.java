package br.com.bpadash.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
public class Bpac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Bpa bpa;
    private String ident;
    private String cnes;
    private String cmp;
    private String cbo;
    private String flh;
    private String seq;
    private String pa;
    private String idade;
    private String qt;
    private String org;
    private String fim;

    public Bpac() {
    }

    public Bpac(Bpa bpa, String ident , String cnes , String cmp , String cbo , String flh , String seq , String pa , String idade , String qt , String org , String fim) {
        this.bpa = bpa;
        this.ident = ident;
        this.cnes = cnes;
        this.cmp = cmp;
        this.cbo = cbo;
        this.flh = flh;
        this.seq = seq;
        this.pa = pa;
        this.idade = idade;
        this.qt = qt;
        this.org = org;
        this.fim = fim;
    }

    public Long getId() {
        return id;
    }

    public Bpa getBpa() {
        return bpa;
    }

    public void setBpa(Bpa bpa) {
        this.bpa = bpa;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getFlh() {
        return flh;
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return
                ident  +
                (cnes.isBlank() ? "       " : cnes) +
                (cmp.isBlank() ? "      " : cmp) +
                (cbo.isBlank() ? "      " : cbo) +
                flh +
                seq +
                (pa.isBlank() ? "          " : pa) +
                (idade.isBlank() ? "000" : idade) +
                (qt.isBlank() ? "      " : qt) +
                org +
                (fim.isBlank() ? "  " : fim);
    }
}
