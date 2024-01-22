package br.com.bpadash.model;

import br.com.bpadash.services.EncryptionService;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
public class Bpai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Bpa bpa;
    @Transient
    private String line;
    private String ident;
    private String cnes;
    private String cmp;
    private String cnsmed;
    private String cbo;
    private String dtaten;
    private String flh;
    private String seq;
    private String pa;
    private String cnspac;
    private String cnspacHas;
    private String sexo;
    private String ibge;
    private String cid;
    private String idade;
    private String qt;
    private String caten;
    private String naut;
    private String org;
    private String nmpac;
    private String dtnasc;
    private String raca;
    private String etnia;
    private String nac;
    private String srv;
    private String clf;
    private String equipeSeq;
    private String equipeArea;
    private String cnpj;
    private String cepPcnte;
    private String logradPcnte;
    private String endPcnte;
    private String complPcnte;
    private String numPcnte;
    private String bairroPcnte;
    private String ddtelPcnte;
    private String emailPcnte;
    private String ine;
    private String fim;

    public Bpai() {
    }

    public Bpai(Bpa bpa, String line, String ident, String cnes, String cmp, String cnsmed, String cbo , String dtaten, String flh , String seq , String pa, String cnspac, String sexo, String ibge, String cid, String idade , String qt , String caten, String naut, String org, String nmpac, String dtnasc, String raca, String etnia, String nac, String srv, String clf, String equipeSeq , String equipeArea , String cnpj, String cepPcnte , String logradPcnte , String endPcnte , String complPcnte , String numPcnte , String bairroPcnte , String ddtelPcnte , String emailPcnte , String ine, String fim) {
        this.bpa = bpa;
        this.line = line;
        this.ident = ident;
        this.cnes = cnes;
        this.cmp = cmp;
        this.cnsmed = cnsmed;
        this.cbo = cbo;
        this.dtaten = dtaten;
        this.flh = flh;
        this.seq = seq;
        this.pa = pa;
        this.cnspac = cnspac;
        this.cnspacHas = cnspac;
        this.sexo = sexo;
        this.ibge = ibge;
        this.cid = cid;
        this.idade = idade;
        this.qt = qt;
        this.caten = caten;
        this.naut = naut;
        this.org = org;
        this.nmpac = nmpac;
        this.dtnasc = dtnasc;
        this.raca = raca;
        this.etnia = etnia;
        this.nac = nac;
        this.srv = srv;
        this.clf = clf;
        this.equipeSeq = equipeSeq;
        this.equipeArea = equipeArea;
        this.cnpj = cnpj;
        this.cepPcnte = cepPcnte;
        this.logradPcnte = logradPcnte;
        this.endPcnte = endPcnte;
        this.complPcnte = complPcnte;
        this.numPcnte = numPcnte;
        this.bairroPcnte = bairroPcnte;
        this.ddtelPcnte = ddtelPcnte;
        this.emailPcnte = emailPcnte;
        this.ine = ine;
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

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    public String getCnsmed() {
        return cnsmed;
    }

    public void setCnsmed(String cnsmed) {
        this.cnsmed = cnsmed;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getDtaten() {
        return dtaten;
    }

    public void setDtaten(String dtaten) {
        this.dtaten = dtaten;
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

    public String getCnspac() {
        return cnspac;
    }

    public void setCnspac(String cnspac) {
        this.cnspac = cnspac;
    }

    public String getCnspacHas() {
        return cnspacHas;
    }

    public void setCnspacHas(String cnspacHas) {
        this.cnspacHas = cnspacHas;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getCaten() {
        return caten;
    }

    public void setCaten(String caten) {
        this.caten = caten;
    }

    public String getNaut() {
        return naut;
    }

    public void setNaut(String naut) {
        this.naut = naut;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getNmpac() {
        return nmpac;
    }

    public void setNmpac(String nmpac) {
        this.nmpac = nmpac;
    }

    public String getDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(String dtnasc) {
        this.dtnasc = dtnasc;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getNac() {
        return nac;
    }

    public void setNac(String nac) {
        this.nac = nac;
    }

    public String getSrv() {
        return srv;
    }

    public void setSrv(String srv) {
        this.srv = srv;
    }

    public String getClf() {
        return clf;
    }

    public void setClf(String clf) {
        this.clf = clf;
    }

    public String getEquipeSeq() {
        return equipeSeq;
    }

    public void setEquipeSeq(String equipeSeq) {
        this.equipeSeq = equipeSeq;
    }

    public String getEquipeArea() {
        return equipeArea;
    }

    public void setEquipeArea(String equipeArea) {
        this.equipeArea = equipeArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCepPcnte() {
        return cepPcnte;
    }

    public void setCepPcnte(String cepPcnte) {
        this.cepPcnte = cepPcnte;
    }

    public String getLogradPcnte() {
        return logradPcnte;
    }

    public void setLogradPcnte(String logradPcnte) {
        this.logradPcnte = logradPcnte;
    }

    public String getEndPcnte() {
        return endPcnte;
    }

    public void setEndPcnte(String endPcnte) {
        this.endPcnte = endPcnte;
    }

    public String getComplPcnte() {
        return complPcnte;
    }

    public void setComplPcnte(String complPcnte) {
        this.complPcnte = complPcnte;
    }

    public String getNumPcnte() {
        return numPcnte;
    }

    public void setNumPcnte(String numPcnte) {
        this.numPcnte = numPcnte;
    }

    public String getBairroPcnte() {
        return bairroPcnte;
    }

    public void setBairroPcnte(String bairroPcnte) {
        this.bairroPcnte = bairroPcnte;
    }

    public String getDdtelPcnte() {
        return ddtelPcnte;
    }

    public void setDdtelPcnte(String ddtelPcnte) {
        this.ddtelPcnte = ddtelPcnte;
    }

    public String getEmailPcnte() {
        return emailPcnte;
    }

    public void setEmailPcnte(String emailPcnte) {
        this.emailPcnte = emailPcnte;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return  ident +
                (cnes.isBlank() ? "       " : cnes) +
                (cmp.isBlank() ? "      " : cmp) +
                (cnsmed.isBlank() ? "               " : cnsmed)  +
                (cbo.isBlank() ? "      " : cbo) +
                (dtaten.isBlank() ? "        " : dtaten) +
                flh +
                seq +
                (pa.isBlank() ? "          " : pa) +
                (cnspac.isBlank() ? "               " : cnspac) +
                sexo +
                (ibge.isBlank() ? "      " : ibge) +
                (cid.isBlank() ? "    " : cid) +
                (idade.isBlank() ? "000" : idade) +
                (qt.isBlank() ? "      " : qt) +
                (caten.isBlank() ? "  " : caten) +
                (naut.isBlank() ? "             " : naut) +
                org +
                (nmpac.isBlank() ? "                              " : nmpac) +
                (dtnasc.isBlank() ? "        " : dtaten) +
                raca +
                (etnia.isBlank() ? "    " : etnia) +
                (nac.isBlank() ? "   " : nac) +
                (srv.isBlank() ? "   " : srv) +
                (clf.isBlank() ? "   " : clf) +
                (equipeSeq.isBlank() ? "        " : equipeSeq) +
                (equipeArea.isBlank() ? "    " : equipeArea) +
                (cnpj.isBlank() ? "              " : cnpj) +
                (cepPcnte.isBlank() ? "        " : cepPcnte) +
                (logradPcnte.isBlank() ? "   " : logradPcnte) +
                (endPcnte.isBlank() ? "                              " : endPcnte) +
                (complPcnte.isBlank() ? "          " : complPcnte) +
                (numPcnte.isBlank() ? "     " : numPcnte) +
                (bairroPcnte.isBlank() ? "                              " : bairroPcnte) +
                (ddtelPcnte.isBlank() ? "           " : ddtelPcnte)+
                (emailPcnte.isBlank() ? "                                        " : emailPcnte) +
                (ine.isBlank() ? "          " : ine) +
                (fim.isBlank() ? "  " : fim);
    }
}
