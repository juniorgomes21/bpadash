package br.com.bpadash.dto.user;

import java.math.BigDecimal;

public class BugetDTO {
    private int totalOrcada;
    private int totalProd;
    private BigDecimal totalValueOrcada;
    private BigDecimal totalValueProd;

    public BugetDTO() {
    }

    public BugetDTO(int totalOrcada , int totalProd , BigDecimal totalValueOrcada , BigDecimal totalValueProd) {
        this.totalOrcada = totalOrcada;
        this.totalProd = totalProd;
        this.totalValueOrcada = totalValueOrcada;
        this.totalValueProd = totalValueProd;
    }

    public int getTotalOrcada() {
        return totalOrcada;
    }

    public void setTotalOrcada(int totalOrcada) {
        this.totalOrcada = totalOrcada;
    }

    public int getTotalProd() {
        return totalProd;
    }

    public void setTotalProd(int totalProd) {
        this.totalProd = totalProd;
    }

    public BigDecimal getTotalValueOrcada() {
        return totalValueOrcada;
    }

    public void setTotalValueOrcada(BigDecimal totalValueOrcada) {
        this.totalValueOrcada = totalValueOrcada;
    }

    public BigDecimal getTotalValueProd() {
        return totalValueProd;
    }

    public void setTotalValueProd(BigDecimal totalValueProd) {
        this.totalValueProd = totalValueProd;
    }
}
