package br.com.bpadash.dto.sigtap;

import br.com.bpadash.model.sigtap.Fpo;

import java.math.BigDecimal;

public class FpoDTO {

    private Long id;
    private int quantOrcada;
    private int quantProd;
    private int quantApro;
    private String pa;
    private String description;
    private String valueUnit;
    private String valueOrcado;
    private String valueProd;
    private String valueApro;


    public FpoDTO() {
    }

    public FpoDTO(Fpo fpo) {
        this.id = fpo.getId();
        this.quantOrcada = fpo.getQuantOrcada();
        this.quantProd = fpo.getQuantProd();
        this.quantApro = fpo.getQuantApro();
        this.pa = fpo.getPa();
        this.description = fpo.getDescription();
        this.valueUnit = String.valueOf(fpo.getValueUnit());
        this.valueOrcado = String.valueOf(fpo.getValueOrcado());
        this.valueProd = String.valueOf(fpo.getValueProd());
        this.valueApro = String.valueOf(fpo.getValueApro());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantOrcada() {
        return quantOrcada;
    }

    public void setQuantOrcada(int quantOrcada) {
        this.quantOrcada = quantOrcada;
    }

    public int getQuantProd() {
        return quantProd;
    }

    public void setQuantProd(int quantProd) {
        this.quantProd = quantProd;
    }

    public int getQuantApro() {
        return quantApro;
    }

    public void setQuantApro(int quantApro) {
        this.quantApro = quantApro;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    public String getValueOrcado() {
        return valueOrcado;
    }

    public void setValueOrcado(String valueOrcado) {
        this.valueOrcado = valueOrcado;
    }

    public String getValueProd() {
        return valueProd;
    }

    public void setValueProd(String valueProd) {
        this.valueProd = valueProd;
    }

    public String getValueApro() {
        return valueApro;
    }

    public void setValueApro(String valueApro) {
        this.valueApro = valueApro;
    }
}
