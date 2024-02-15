package br.com.bpadash.params.bpa;

import br.com.bpadash.validations.bpa.BpaValid;
import br.com.bpadash.validations.bpa.org.OrgValid;
import br.com.bpadash.validations.bpa.race.RaceValid;
import br.com.bpadash.validations.bpa.sexo.SexoValid;

import javax.validation.constraints.NotBlank;

public class ParamUpdateBpai {
    //TODO alguns campos não podem ser brancos
    @NotBlank(message = "O Campo IDENT não pode ser branco")
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. O campo deverá ser preenchido apenas com números.")
    private String ident;
    @BpaValid(size = 7, message = "O campo CNES deve conter 7 caracteres. O campo deverá ser preenchido apenas com números adicionar zeros à esquerda.")
    private String cnes;
    @BpaValid(size = 6, message = "O campo CMP deve conter 6 caracteres. O campo deverá ser preenchido apenas com números formato AAAAMM.")
    private String cmp;
    @BpaValid(size = 15, message = "O campo CNSMED deve conter 15 caracteres. O campo deverá ser preenchido apenas com números.")
    private String cnsmed;
    @BpaValid(size = 6, alfa = true, message = "O campo CBO deve conter 6 caracteres. Código conforme a Classificação Brasileira de ocupações (CBO).")
    private String cbo;
    @BpaValid(size = 8, message = "O campo DTATEN deve conter 8 caracteres. O campo deverá ser preenchido apenas com números formato AAAAMMDD")
    private String dtaten;
    @BpaValid(size = 3, message = "O campo FLH deve conter 3 caracteres. Adicionar zeros à esquerda de um inteiro.")
    private String flh;
    @BpaValid(size = 2, message = "O campo SEQ deve conter 2 caracteres. Adicionar zeros à esquerda de um inteiro.")
    private String seq;
    @BpaValid(size = 10, message = "O campo PA deve conter 10 caracteres. Adicionar zeros à esquerda.")
    private String pa;
    @BpaValid(size = 15, message = "O campo CNSPAC deve conter 15 caracteres. Este campo é obrigatório quando o procedimento informado exigir e deverá ser preenchido apenas com números.")
    private String cnspac;
    @SexoValid
    private String sexo;
    @BpaValid(size = 6, message = "O campo IBGE deve conter 6 caracteres. Quando preenchido, deverá ser apenas com números.")
    private String ibge;
    @BpaValid(size = 4, message = "O campo CID deve conter 4 caracteres.")
    private String cid;
    @BpaValid(size = 3, message = "O campo IDADE deve conter 3 caracteres.")
    private String idade;
    @BpaValid(size = 6, message = "O campo QT deve conter 6 caracteres. Adicionar zeros à esquerda de um inteiro.")
    private String qt;
    @BpaValid(size = 2, message = "O campo CATEN deve conter 2 caracteres. Quando preenchido, deverá ser apenas com números adicionar zeros à esquerda.")
    private String caten;
    @BpaValid(size = 13, message = "O campo NAUT deve conter 13 caracteres. Quando preenchido, deverá ser apenas com números.")
    private String naut;
    @OrgValid
    private String org;
    @BpaValid(size = 30, alfa = true, message = "O campo NMPAC deve conter 30 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String nmpac;
    @BpaValid(size = 8, message = "O campo DTNASC deve conter 8 caracteres. Formato AAAAMMDD.")
    private String dtnasc;
    @RaceValid
    private String raca;
    @BpaValid(size = 4, message = "O campo ETNIA deve conter 4 caracteres. Quando preenchido, deverá conter apenas números.")
    private String etnia;
    @BpaValid(size = 3, message = "O campo NAC deve conter 3 caracteres. Quando preenchido, deverá conter apenas números.")
    private String nac;
    @BpaValid(size = 3, message = "O campo SRV deve conter 3 caracteres. Quando preenchido, deverá conter apenas números.")
    private String srv;
    @BpaValid(size = 3, message = "O campo CLF deve conter 3 caracteres. Quando preenchido, deverá conter apenas números.")
    private String clf;
    @BpaValid(size = 8, message = "O campo EQUIPESEQ deve conter 8 caracteres. Quando preenchido, deverá conter apenas números.")
    private String equipeSeq;
    @BpaValid(size = 4, message = "O campo EQUIPEAREA deve conter 4 caracteres. Quando preenchido, deverá conter apenas números.")
    private String equipeArea;
    @BpaValid(size = 14, message = "O campo CNPJ deve conter 14 caracteres. Quando preenchido, deverá conter apenas números.")
    private String cnpj;
    @BpaValid(size = 8, message = "O campo CEP_PCNTE deve conter 8 caracteres. Quando preenchido, deverá conter apenas números.")
    private String cepPcnte;
    @BpaValid(size = 3, message = "O campo LOGRAD_PCNTE deve conter 3 caracteres. Quando preenchido, deverá conter apenas números.")
    private String logradPcnte;
    @BpaValid(size = 30, alfa = true, message = "O campo END_PCNTE deve conter 30 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String endPcnte;
    @BpaValid(size = 10, alfa = true, message = "O campo COMPL_PCNTE deve conter 10 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String complPcnte;
    @BpaValid(size = 5, alfa = true, message = "O campo NUM_PCNTE deve conter 5 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String numPcnte;
    @BpaValid(size = 30, alfa = true, message = "O campo BAIRRO_PCNTE deve conter 30 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String bairroPcnte;
    @BpaValid(size = 11, message = "O campo DDTEL_PCNTE deve conter 11 caracteres. Quando preenchido, deverá conter apenas números. Senão preencher com branco a direita até completar total caracteres.")
    private String ddtelPcnte;
    @BpaValid(size = 40, alfa = true, message = "O campo EMAIL_PCNTE deve conter 40 caracteres. Adicionar espaço em branco a direita até completar total caracteres.")
    private String emailPcnte;
    @BpaValid(size = 10, message = "O campo INE deve conter 10 caracteres. Quando preenchido, deverá conter apenas números. Adicionar zeros à esquerda. Apartir da competência 08/2015")
    private String ine;
    @BpaValid(size = 2, message = "O campo FIM deve conter 2 caracteres.")
    private String fim;

    public ParamUpdateBpai() {
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
        return sexo.toUpperCase();
    }

    public void setSexo(String sexo) {
        this.sexo = sexo.toUpperCase();
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
