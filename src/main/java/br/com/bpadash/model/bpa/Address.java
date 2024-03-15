package br.com.bpadash.model.bpa;

import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.AddressUser;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep = "";
    private String logradouro = "";
    private String complemento = "";
    private String bairro = "";
    private String localidade = "";
    private String uf = "";
    private String ibge = "";
    private String gia = "";
    private String ddd = "";
    private String siafi = "";
    private String codLograud = "008";
    private boolean isBpai;

    public Address() {
    }

    public Address(String cep) {
        this.cep = cep;
    }

    public Address(AddressUser addressUser) {
        this.cep = addressUser.getCep();
        this.codLograud = addressUser.getCodLograud();
        this.logradouro = addressUser.getLogradouro();
        this.complemento = addressUser.getComplemento();
        this.bairro = addressUser.getBairro();
        this.localidade = "Belém";
        this.uf = "PA";
        this.ibge = addressUser.getIbge();
        this.gia = "";
        this.ddd = "091";
        this.siafi = "";
        this.isBpai = true;
    }

    public Address(Bpai bpai) {
        this.cep = bpai.getCepPcnte();
        this.codLograud = bpai.getLogradPcnte();
        this.logradouro = bpai.getEndPcnte().trim();
        this.complemento = bpai.getComplPcnte().trim();
        this.bairro = bpai.getBairroPcnte().trim();
        this.localidade = "Belém";
        this.uf = "PA";
        this.ibge = bpai.getIbge();
        this.gia = "";
        this.ddd = "091";
        this.siafi = "";
        this.isBpai = true;
    }


    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getSiafi() {
        return siafi;
    }

    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodLograud() {
        return codLograud;
    }

    public void setCodLograud(String codLograud) {
        this.codLograud = codLograud;
    }

    public boolean isBpai() {
        return isBpai;
    }

    public void setBpai(boolean bpai) {
        isBpai = bpai;
    }
}
