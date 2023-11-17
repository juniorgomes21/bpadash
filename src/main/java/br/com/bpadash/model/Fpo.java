package br.com.bpadash.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Fpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pa;
    private String description;
    private int quantOrcada;
    private BigDecimal valueUnit;
    private BigDecimal valueOrcado;
    private int quantProd;
    private BigDecimal valueProd;
    private int quantApro;
    private BigDecimal valueApro;
    @ManyToOne
    private LinkFpo linkFpo;

    public Fpo() {
    }

    public Fpo(LinkFpo linkFpo, String pa, String description , String quantOrcada , String valueUnit , String valueOrcado , String quantProd , String valueProd , String quantApro , String valueApro) {
        this.pa = pa;
        this.linkFpo = linkFpo;
        this.description = description.isEmpty() ? "Sem descrição" : description;
        this.quantOrcada = Integer.parseInt(quantOrcada.isEmpty() ? "0" : quantOrcada);
        this.valueUnit = BigDecimal.valueOf(Double.parseDouble(valueUnit.isEmpty() ? "0" : valueUnit));
        this.valueOrcado = BigDecimal.valueOf(Double.parseDouble(valueOrcado.isEmpty() ? "0" : valueOrcado));
        this.quantProd = Integer.parseInt(quantProd.isEmpty() ? "0" : quantProd);
        this.valueProd = BigDecimal.valueOf(Double.parseDouble(valueProd.isEmpty() ? "0" : valueProd));
        this.quantApro = Integer.parseInt(quantApro.isEmpty() ? "0" : quantApro);
        this.valueApro = BigDecimal.valueOf(Double.parseDouble(valueApro.isEmpty() ? "0" : valueApro));
    }



    public Long getId() {
        return id;
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

    public int getQuantOrcada() {
        return quantOrcada;
    }

    public void setQuantOrcada(int quantOrcada) {
        this.quantOrcada = quantOrcada;
    }

    public BigDecimal getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(BigDecimal valueUnit) {
        this.valueUnit = valueUnit;
    }

    public BigDecimal getValueOrcado() {
        return valueOrcado;
    }

    public void setValueOrcado(BigDecimal valueOrcado) {
        this.valueOrcado = valueOrcado;
    }

    public int getQuantProd() {
        return quantProd;
    }

    public void setQuantProd(int quantProd) {
        this.quantProd = quantProd;
    }

    public BigDecimal getValueProd() {
        return valueProd;
    }

    public void setValueProd(BigDecimal valueProd) {
        this.valueProd = valueProd;
    }

    public int getQuantApro() {
        return quantApro;
    }

    public void setQuantApro(int quantApro) {
        this.quantApro = quantApro;
    }

    public BigDecimal getValueApro() {
        return valueApro;
    }

    public void setValueApro(BigDecimal valueApro) {
        this.valueApro = valueApro;
    }

    public LinkFpo getLinkFpo() {
        return linkFpo;
    }

    public void setLinkFpo(LinkFpo linkFpo) {
        this.linkFpo = linkFpo;
    }

    @Override
    public String toString() {
        return "Fpo{" +
                "pa='" + pa + '\'' +
                ", description='" + description + '\'' +
                ", quantOrcada=" + quantOrcada +
                ", valueUnit=" + valueUnit +
                ", valueOrcado=" + valueOrcado +
                ", quantProd=" + quantProd +
                ", valueProd=" + valueProd +
                ", quantApro=" + quantApro +
                ", valueApro=" + valueApro +
                '}';
    }
}
