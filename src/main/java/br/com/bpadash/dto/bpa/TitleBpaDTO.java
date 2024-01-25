package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.TitleBpa;

public class TitleBpaDTO {

    private Long id;
    private String iden;
    private String hdr;
    private String mvm;
    private String lin;
    private String flh;
    private String smtVrf;
    private String rsp;
    private String sgl;
    private String cgccpf;
    private String dst;
    private String dstIn;
    private String versao;
    private String fim;
    private int countRules;

    public TitleBpaDTO() {
    }

    public TitleBpaDTO(TitleBpa titleBpa, int countRules) {
        this.id = titleBpa.getId();
        this.iden = titleBpa.getIden();
        this.hdr = titleBpa.getHdr();
        this.mvm = titleBpa.getMvm();
        this.lin = titleBpa.getLin();
        this.flh = titleBpa.getFlh();
        this.smtVrf = titleBpa.getSmtVrf();
        this.rsp = titleBpa.getRsp();
        this.sgl = titleBpa.getSgl();
        this.cgccpf = titleBpa.getCgccpf();
        this.dst = titleBpa.getDst();
        this.dstIn = titleBpa.getDstIn();
        this.versao = titleBpa.getVersao();
        this.fim = titleBpa.getFim();
        this.countRules = countRules;
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

    public String getHdr() {
        return hdr;
    }

    public void setHdr(String hdr) {
        this.hdr = hdr;
    }

    public String getMvm() {
        return mvm;
    }

    public void setMvm(String mvm) {
        this.mvm = mvm;
    }

    public String getLin() {
        return lin;
    }

    public void setLin(String lin) {
        this.lin = lin;
    }

    public String getFlh() {
        return flh;
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public String getSmtVrf() {
        return smtVrf;
    }

    public void setSmtVrf(String smtVrf) {
        this.smtVrf = smtVrf;
    }

    public String getRsp() {
        return rsp;
    }

    public void setRsp(String rsp) {
        this.rsp = rsp;
    }

    public String getSgl() {
        return sgl;
    }

    public void setSgl(String sgl) {
        this.sgl = sgl;
    }

    public String getCgccpf() {
        return cgccpf;
    }

    public void setCgccpf(String cgccpf) {
        this.cgccpf = cgccpf;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDstIn() {
        return dstIn;
    }

    public void setDstIn(String dstIn) {
        this.dstIn = dstIn;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public int getCountRules() {
        return countRules;
    }

    public void setCountRules(int countRules) {
        this.countRules = countRules;
    }
}
