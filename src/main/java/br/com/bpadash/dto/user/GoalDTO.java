package br.com.bpadash.dto.user;

import br.com.bpadash.model.sigtap.Fpo;

import java.math.BigDecimal;

public class GoalDTO {
    private String pa;
    private String name;
    private int quantProd;
    private int quantOrcada;
    private double percent;
    private BigDecimal valueUnit;
    private BigDecimal valueTotal;
    private boolean error;

    public GoalDTO() {
    }

    public GoalDTO(String pa, int quantOrcada, int quantProd , double percent, BigDecimal valueTotal , boolean error) {
        this.pa = pa;
        this.quantProd = quantProd;
        this.quantOrcada = quantOrcada;
        this.percent = percent;
        this.valueTotal = valueTotal;
        this.error = error;
    }

    public GoalDTO(Fpo fpo, BigDecimal valueTotal, double percent, boolean error) {
        this.pa = fpo.getPa();
        this.name = fpo.getDescription();
        this.quantProd = fpo.getQuantProd();
        this.quantOrcada = fpo.getQuantOrcada();
        this.valueUnit = fpo.getValueUnit();
        this.percent = percent;
        this.error = error;
        this.valueTotal = valueTotal;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantProd() {
        return quantProd;
    }

    public void setQuantProd(int quantProd) {
        this.quantProd = quantProd;
    }

    public int getQuantOrcada() {
        return quantOrcada;
    }

    public void setQuantOrcada(int quantOrcada) {
        this.quantOrcada = quantOrcada;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public BigDecimal getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(BigDecimal valueUnit) {
        this.valueUnit = valueUnit;
    }

    public BigDecimal getValueTotal() {
        return valueTotal;
    }

    public void setValueTotal(BigDecimal valueTotal) {
        this.valueTotal = valueTotal;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
