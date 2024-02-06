package br.com.bpadash.model.bpa;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ManagerBpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal invoicing = BigDecimal.ZERO;
    private int countTotalLine = 0;
    private int countLineBpac = 0;
    private int countLineBpai = 0;
    private int	old = 0;
    private int	yong = 0;
    private int	middleAge = 0;
    private int sexM = 0;
    private int sexF = 0;
    private boolean calculateInvoicing = true;
    private boolean calculateCountLine = true;
    private boolean calculateAge = true;
    private boolean calculateSex = true;

    public ManagerBpa() {
    }


    public Long getId() {
        return id;
    }

    public BigDecimal getInvoicing() {
        return invoicing;
    }

    public void setInvoicing(BigDecimal invoicing) {
        this.invoicing = invoicing;
    }

    public int getCountTotalLine() {
        return countTotalLine;
    }

    public void setCountTotalLine(int countTotalLine) {
        this.countTotalLine = countTotalLine;
    }

    public int getCountLineBpac() {
        return countLineBpac;
    }

    public void setCountLineBpac(int countLineBpac) {
        this.countLineBpac = countLineBpac;
    }

    public int getCountLineBpai() {
        return countLineBpai;
    }

    public void setCountLineBpai(int countLineBpai) {
        this.countLineBpai = countLineBpai;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public int getYong() {
        return yong;
    }

    public void setYong(int yong) {
        this.yong = yong;
    }

    public int getMiddleAge() {
        return middleAge;
    }

    public void setMiddleAge(int middleAge) {
        this.middleAge = middleAge;
    }

    public int getSexM() {
        return sexM;
    }

    public void setSexM(int sexM) {
        this.sexM = sexM;
    }

    public int getSexF() {
        return sexF;
    }

    public void setSexF(int sexF) {
        this.sexF = sexF;
    }

    public boolean isCalculateInvoicing() {
        return calculateInvoicing;
    }

    public void setCalculateInvoicing(boolean calculateInvoicing) {
        this.calculateInvoicing = calculateInvoicing;
    }

    public boolean isCalculateCountLine() {
        return calculateCountLine;
    }

    public void setCalculateCountLine(boolean calculateCountLine) {
        this.calculateCountLine = calculateCountLine;
    }

    public boolean isCalculateAge() {
        return calculateAge;
    }

    public void setCalculateAge(boolean calculateAge) {
        this.calculateAge = calculateAge;
    }

    public boolean isCalculateSex() {
        return calculateSex;
    }

    public void setCalculateSex(boolean calculateSex) {
        this.calculateSex = calculateSex;
    }
}
