package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.BpaiValidation;

public class BpaiValidationDTO {
    private boolean cnes;
    private boolean cmp;
    private boolean cnsmed;
    private boolean cbo;
    private boolean dtaten;
    private boolean pa;
    private boolean cnspac;
    private boolean sexo;
    private boolean ibge;
    private boolean cid;
    private boolean idade;
    private boolean qt;
    private boolean caten;
    private boolean naut;
    private boolean org;
    private boolean nmpac;
    private boolean dtnasc;
    private boolean raca;
    private boolean etnia;
    private boolean nac;
    private boolean srv;
    private boolean clf;
    private boolean equipeSeq;
    private boolean equipeArea;
    private boolean cnpj;
    private boolean cepPcnte;
    private boolean logradPcnte;
    private boolean endPcnte;
    private boolean complPcnte;
    private boolean numPcnte;
    private boolean bairroPcnte;
    private boolean ddtelPcnte;
    private boolean emailPcnte;
    private boolean ine;

    public BpaiValidationDTO() {
    }

    public BpaiValidationDTO(BpaiValidation bpaiValidation) {
        this.cnes = bpaiValidation.isCnes();
        this.cmp = bpaiValidation.isCmp();
        this.cnsmed = bpaiValidation.isCnsmed();
        this.cbo = bpaiValidation.isCbo();
        this.dtaten = bpaiValidation.isDtaten();
        this.pa = bpaiValidation.isPa();
        this.cnspac = bpaiValidation.isCnspac();
        this.sexo = bpaiValidation.isSexo();
        this.ibge = bpaiValidation.isIbge();
        this.cid = bpaiValidation.isCid();
        this.idade = bpaiValidation.isIdade();
        this.qt = bpaiValidation.isQt();
        this.caten = bpaiValidation.isCaten();
        this.naut = bpaiValidation.isNaut();
        this.org = bpaiValidation.isOrg();
        this.nmpac = bpaiValidation.isNmpac();
        this.dtnasc = bpaiValidation.isDtnasc();
        this.raca = bpaiValidation.isRaca();
        this.etnia = bpaiValidation.isEtnia();
        this.nac = bpaiValidation.isNac();
        this.srv = bpaiValidation.isSrv();
        this.clf = bpaiValidation.isClf();
        this.equipeSeq = bpaiValidation.isEquipeSeq();
        this.equipeArea = bpaiValidation.isEquipeArea();
        this.cnpj = bpaiValidation.isCnpj();
        this.cepPcnte = bpaiValidation.isCepPcnte();
        this.logradPcnte = bpaiValidation.isLogradPcnte();
        this.endPcnte = bpaiValidation.isEndPcnte();
        this.complPcnte = bpaiValidation.isComplPcnte();
        this.numPcnte = bpaiValidation.isNumPcnte();
        this.bairroPcnte = bpaiValidation.isBairroPcnte();
        this.ddtelPcnte = bpaiValidation.isDdtelPcnte();
        this.emailPcnte = bpaiValidation.isEmailPcnte();
        this.ine = bpaiValidation.isIne();



    }

    public boolean isCnes() {
        return cnes;
    }

    public void setCnes(boolean cnes) {
        this.cnes = cnes;
    }

    public boolean isCmp() {
        return cmp;
    }

    public void setCmp(boolean cmp) {
        this.cmp = cmp;
    }

    public boolean isCnsmed() {
        return cnsmed;
    }

    public void setCnsmed(boolean cnsmed) {
        this.cnsmed = cnsmed;
    }

    public boolean isCbo() {
        return cbo;
    }

    public void setCbo(boolean cbo) {
        this.cbo = cbo;
    }

    public boolean isDtaten() {
        return dtaten;
    }

    public void setDtaten(boolean dtaten) {
        this.dtaten = dtaten;
    }

    public boolean isPa() {
        return pa;
    }

    public void setPa(boolean pa) {
        this.pa = pa;
    }

    public boolean isCnspac() {
        return cnspac;
    }

    public void setCnspac(boolean cnspac) {
        this.cnspac = cnspac;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isIbge() {
        return ibge;
    }

    public void setIbge(boolean ibge) {
        this.ibge = ibge;
    }

    public boolean isCid() {
        return cid;
    }

    public void setCid(boolean cid) {
        this.cid = cid;
    }

    public boolean isIdade() {
        return idade;
    }

    public void setIdade(boolean idade) {
        this.idade = idade;
    }

    public boolean isQt() {
        return qt;
    }

    public void setQt(boolean qt) {
        this.qt = qt;
    }

    public boolean isCaten() {
        return caten;
    }

    public void setCaten(boolean caten) {
        this.caten = caten;
    }

    public boolean isNaut() {
        return naut;
    }

    public void setNaut(boolean naut) {
        this.naut = naut;
    }

    public boolean isOrg() {
        return org;
    }

    public void setOrg(boolean org) {
        this.org = org;
    }

    public boolean isNmpac() {
        return nmpac;
    }

    public void setNmpac(boolean nmpac) {
        this.nmpac = nmpac;
    }

    public boolean isDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(boolean dtnasc) {
        this.dtnasc = dtnasc;
    }

    public boolean isRaca() {
        return raca;
    }

    public void setRaca(boolean raca) {
        this.raca = raca;
    }

    public boolean isEtnia() {
        return etnia;
    }

    public void setEtnia(boolean etnia) {
        this.etnia = etnia;
    }

    public boolean isNac() {
        return nac;
    }

    public void setNac(boolean nac) {
        this.nac = nac;
    }

    public boolean isSrv() {
        return srv;
    }

    public void setSrv(boolean srv) {
        this.srv = srv;
    }

    public boolean isClf() {
        return clf;
    }

    public void setClf(boolean clf) {
        this.clf = clf;
    }

    public boolean isEquipeSeq() {
        return equipeSeq;
    }

    public void setEquipeSeq(boolean equipeSeq) {
        this.equipeSeq = equipeSeq;
    }

    public boolean isEquipeArea() {
        return equipeArea;
    }

    public void setEquipeArea(boolean equipeArea) {
        this.equipeArea = equipeArea;
    }

    public boolean isCnpj() {
        return cnpj;
    }

    public void setCnpj(boolean cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isCepPcnte() {
        return cepPcnte;
    }

    public void setCepPcnte(boolean cepPcnte) {
        this.cepPcnte = cepPcnte;
    }

    public boolean isLogradPcnte() {
        return logradPcnte;
    }

    public void setLogradPcnte(boolean logradPcnte) {
        this.logradPcnte = logradPcnte;
    }

    public boolean isEndPcnte() {
        return endPcnte;
    }

    public void setEndPcnte(boolean endPcnte) {
        this.endPcnte = endPcnte;
    }

    public boolean isComplPcnte() {
        return complPcnte;
    }

    public void setComplPcnte(boolean complPcnte) {
        this.complPcnte = complPcnte;
    }

    public boolean isNumPcnte() {
        return numPcnte;
    }

    public void setNumPcnte(boolean numPcnte) {
        this.numPcnte = numPcnte;
    }

    public boolean isBairroPcnte() {
        return bairroPcnte;
    }

    public void setBairroPcnte(boolean bairroPcnte) {
        this.bairroPcnte = bairroPcnte;
    }

    public boolean isDdtelPcnte() {
        return ddtelPcnte;
    }

    public void setDdtelPcnte(boolean ddtelPcnte) {
        this.ddtelPcnte = ddtelPcnte;
    }

    public boolean isEmailPcnte() {
        return emailPcnte;
    }

    public void setEmailPcnte(boolean emailPcnte) {
        this.emailPcnte = emailPcnte;
    }

    public boolean isIne() {
        return ine;
    }

    public void setIne(boolean ine) {
        this.ine = ine;
    }

}
