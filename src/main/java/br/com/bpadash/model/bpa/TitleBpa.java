package br.com.bpadash.model.bpa;

import br.com.bpadash.model.bpa.Bpa;

import javax.persistence.*;

@Entity
public class TitleBpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Bpa bpa;
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

    public TitleBpa() {
    }

    public TitleBpa(Bpa bpa, String iden , String hdr , String mvm , String lin , String flh , String smtVrf , String rsp , String sgl , String cgccpf , String dst , String dstIn , String versao , String fim) {
        this.bpa = bpa;
        this.iden = iden;
        this.hdr = hdr;
        this.mvm = mvm;
        this.lin = lin;
        this.flh = flh;
        this.smtVrf = smtVrf;
        this.rsp = rsp;
        this.sgl = sgl;
        this.cgccpf = cgccpf;
        this.dst = dst;
        this.dstIn = dstIn;
        this.versao = versao;
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

    @Override
    public String toString() {
        return  iden +
                hdr +
                mvm +
                lin +
                flh +
                smtVrf +
                rsp +
                sgl +
                cgccpf +
                dst +
                dstIn +
                versao +
                fim;
    }
}
