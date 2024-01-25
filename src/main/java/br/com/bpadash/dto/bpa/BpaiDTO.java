package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.Bpai;

public class BpaiDTO {
    private Long id;
    private String identifier;
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

    public BpaiDTO() {
    }

    public BpaiDTO(Bpai bpai, String identifier) {
        this.id = bpai.getId();
        this.identifier = identifier;
        this.ident = bpai.getIdent();
        this.cnes = bpai.getCnes();
        this.cmp = bpai.getCmp();
        this.cnsmed = bpai.getCnsmed();
        this.cbo = bpai.getCbo();
        this.dtaten = bpai.getDtaten();
        this.flh = bpai.getFlh();
        this.seq = bpai.getSeq();
        this.pa = bpai.getPa();
        this.cnspac = bpai.getCnspac();
        this.sexo = bpai.getSexo();
        this.ibge = bpai.getIbge();
        this.cid = bpai.getCid();
        this.idade = bpai.getIdade();
        this.qt = bpai.getQt();
        this.caten = bpai.getCaten();
        this.naut = bpai.getNaut();
        this.org = bpai.getOrg();
        this.nmpac = bpai.getNmpac();
        this.dtnasc = bpai.getDtnasc();
        this.raca = bpai.getRaca();
        this.etnia = bpai.getEtnia();
        this.nac = bpai.getNac();
        this.srv = bpai.getSrv();
        this.clf = bpai.getClf();
        this.equipeSeq = bpai.getEquipeSeq();
        this.equipeArea = bpai.getEquipeArea();
        this.cnpj = bpai.getCnpj();
        this.cepPcnte = bpai.getCepPcnte();
        this.logradPcnte = bpai.getLogradPcnte();
        this.endPcnte = bpai.getEndPcnte();
        this.complPcnte = bpai.getComplPcnte();
        this.numPcnte = bpai.getNumPcnte();
        this.bairroPcnte = bpai.getBairroPcnte();
        this.ddtelPcnte = bpai.getDdtelPcnte();
        this.emailPcnte = bpai.getEmailPcnte();
        this.ine = bpai.getIne();
        this.fim = bpai.getFim();
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

}
