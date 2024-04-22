package br.com.bpadash.params.bpa;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParamFilterCriteria {
    @NotNull
    @Size(max = 10, message = "Tamanho máximo de 10 caracteres")
    private String pa;
    @NotNull
    @Size(max = 7, message = "Tamanho máximo de 10 caracteres")
    private String cnes;
    @NotNull
    @Size(max = 15, message = "Tamanho máximo de 10 caracteres")
    private String cnsmed;
    @NotNull
    @Size(max = 6, message = "Tamanho máximo de 10 caracteres")
    private String cbo;
    @NotNull
    @Size(max = 6, message = "Tamanho máximo de 10 caracteres")
    private String ibge;
    @NotNull
    @Size(max = 1, message = "Tamanho máximo de 10 caracteres")
    private String sex;
    @NotNull
    @Size(max = 2, message = "Tamanho máximo de 10 caracteres")
    private String race;

    public ParamFilterCriteria() {
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
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

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
