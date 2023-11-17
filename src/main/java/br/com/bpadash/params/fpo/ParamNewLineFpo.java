package br.com.bpadash.params.fpo;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ParamNewLineFpo {
    @Size(min = 9, max = 9, message = "O campo tem que ter 9 digítos")
    private String pa;
    @Size(max=61, message = "O campo não pode ter mais do que 61 caracteres")
    private String description;
    @PositiveOrZero
    private int quantOrcada;
    @DecimalMin(value = "0", message = "O número deve ser positivo ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O número deve ter no máximo 2 casas decimais")
    private BigDecimal valueUnit;
    @DecimalMin(value = "0", message = "O número deve ser positivo ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O número deve ter no máximo 2 casas decimais")
    private BigDecimal valueOrcado;
    @PositiveOrZero
    private int quantProd;
    @DecimalMin(value = "0", message = "O número deve ser positivo ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O número deve ter no máximo 2 casas decimais")
    private BigDecimal valueProd;
    @PositiveOrZero
    private int quantApro;
    @DecimalMin(value = "0", message = "O número deve ser positivo ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O número deve ter no máximo 2 casas decimais")
    private BigDecimal valueApro;

    public ParamNewLineFpo() {
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
}
