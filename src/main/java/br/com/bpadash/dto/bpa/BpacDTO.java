package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.Bpac;

public class BpacDTO {
    private Long id;
    private String identifier;
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

    public BpacDTO() {
    }

    public BpacDTO(Bpac bpac, String identifier) {
        this.id = bpac.getId();
        this.identifier = identifier;
        this.ident = bpac.getIdent();
        this.cnes = bpac.getCnes();
        this.cmp = bpac.getCmp();
        this.cbo = bpac.getCbo();
        this.flh = bpac.getFlh();
        this.seq = bpac.getSeq();
        this.pa = bpac.getPa();
        this.idade = bpac.getIdade();
        this.qt = bpac.getQt();
        this.org = bpac.getOrg();
        this.fim = bpac.getFim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
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

}
