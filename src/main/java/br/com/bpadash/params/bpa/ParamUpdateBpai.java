package br.com.bpadash.params.bpa;

import br.com.bpadash.validations.bpa.BpaValid;
import br.com.bpadash.validations.bpa.org.OrgValid;
import br.com.bpadash.validations.bpa.race.RaceValid;
import br.com.bpadash.validations.bpa.sexo.SexoValid;

public class ParamUpdateBpai {
    //TODO alguns campos não podem ser brancos
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. O campo deverá ser preenchido apenas com números.")
    private String ident;
    @BpaValid(size = 7, message = "O tamanho do campo deve ser 7. O campo deverá ser preenchido apenas com números adicionar zeros à esquerda.")
    private String cnes;
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. O campo deverá ser preenchido apenas com números formato AAAAMM.")
    private String cmp;
    @BpaValid(size = 15, message = "O tamanho do campo deve ser 15. O campo deverá ser preenchido apenas com números.")
    private String cnsmed;
    @BpaValid(size = 6, alfa = true, message = "O tamanho do campo deve ser 6. Código conforme a Classificação Brasileira de ocupações (CBO).")
    private String cbo;
    @BpaValid(size = 8, message = "O tamanho do campo deve ser 8. O campo deverá ser preenchido apenas com números formato AAAAMMDD")
    private String dtaten;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Adicionar zeros à esquerda de um inteiro.")
    private String flh;
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. Adicionar zeros à esquerda de um inteiro.")
    private String seq;
    @BpaValid(size = 10, message = "O tamanho do campo deve ser 10. Adicionar zeros à esquerda.")
    private String pa;
    @BpaValid(size = 15, message = "O tamanho do campo deve ser 15. Este campo é obrigatório quando o procedimento informado exigir e deverá ser preenchido apenas com números.")
    private String cnspac;
    @SexoValid
    private String sexo;
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. Quando preenchido, deverá ser apenas com números.")
    private String ibge;
    @BpaValid(size = 4, message = "O tamanho do campo deve ser 4.")
    private String cid;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3.")
    private String idade;
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. Adicionar zeros à esquerda de um inteiro.")
    private String qt;
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. Quando preenchido, deverá ser apenas com números adicionar zeros à esquerda.")
    private String caten;
    @BpaValid(size = 13, message = "O tamanho do campo deve ser 13. Quando preenchido, deverá ser apenas com números.")
    private String naut;
    @OrgValid
    private String org;
    @BpaValid(size = 30, alfa = true, message = "O tamanho do campo deve ser 30. Adicionar espaço em branco a direita até completar total caracteres.")
    private String nmpac;
    @BpaValid(size = 8, message = "O tamanho do campo deve ser 8. Formato AAAAMMDD.")
    private String dtnasc;
    @RaceValid
    private String raca;
    @BpaValid(size = 4, message = "O tamanho do campo deve ser 4. Quando preenchido, deverá conter apenas números.")
    private String etnia;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Quando preenchido, deverá conter apenas números.")
    private String nac;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Quando preenchido, deverá conter apenas números.")
    private String srv;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Quando preenchido, deverá conter apenas números.")
    private String clf;
    @BpaValid(size = 8, message = "O tamanho do campo deve ser 8. Quando preenchido, deverá conter apenas números.")
    private String equipeSeq;
    @BpaValid(size = 4, message = "O tamanho do campo deve ser 4. Quando preenchido, deverá conter apenas números.")
    private String equipeArea;
    @BpaValid(size = 14, message = "O tamanho do campo deve ser 14. Quando preenchido, deverá conter apenas números.")
    private String cnpj;
    @BpaValid(size = 8, message = "O tamanho do campo deve ser 8. Quando preenchido, deverá conter apenas números.")
    private String cepPcnte;
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Quando preenchido, deverá conter apenas números.")
    private String logradPcnte;
    @BpaValid(size = 30, alfa = true, message = "O tamanho do campo deve ser 30. Adicionar espaço em branco a direita até completar total caracteres.")
    private String endPcnte;
    @BpaValid(size = 10, alfa = true, message = "O tamanho do campo deve ser 10. Adicionar espaço em branco a direita até completar total caracteres.")
    private String complPcnte;
    @BpaValid(size = 5, alfa = true, message = "O tamanho do campo deve ser 5. Adicionar espaço em branco a direita até completar total caracteres.")
    private String numPcnte;
    @BpaValid(size = 30, alfa = true, message = "O tamanho do campo deve ser 30. Adicionar espaço em branco a direita até completar total caracteres.")
    private String bairroPcnte;
    @BpaValid(size = 11, message = "O tamanho do campo deve ser 11. Quando preenchido, deverá conter apenas números. Senão preencher com branco a direita até completar total caracteres.")
    private String ddtelPcnte;
    @BpaValid(size = 40, alfa = true, message = "O tamanho do campo deve ser 40. Adicionar espaço em branco a direita até completar total caracteres.")
    private String emailPcnte;
    @BpaValid(size = 10, message = "O tamanho do campo deve ser 10. Quando preenchido, deverá conter apenas números. Adicionar zeros à esquerda. Apartir da competência 08/2015")
    private String ine;

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
}
