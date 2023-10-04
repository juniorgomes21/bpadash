package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.Bpac;

import java.util.ArrayList;
import java.util.List;

public class BpacDTO {
    private Long id;
    private String iden;
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

    public BpacDTO(Bpac bpac) {
        this.id = bpac.getId();
        this.iden = bpac.getIden();
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

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
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

    public static List<BpacDTO> listDTO(List<Bpac> list) {
        List<BpacDTO> bpacDTOS = new ArrayList<>();

        list.forEach( bpac -> {
            bpacDTOS.add(new BpacDTO(bpac));
        });

        return bpacDTOS;
    }
}
