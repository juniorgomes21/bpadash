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
    private int	blank = 0;
    private int	black = 0;
    private int	brown = 0;
    private int	yellow = 0;
    private int	Indigenous = 0;
    private int	noInformation = 0;
    private int	middleAge = 0;
    private int sexM = 0;
    private int sexF = 0;
    private boolean calculateInvoicing = false;
    private boolean calculateCountLine = false;
    private boolean calculateAge = false;
    private boolean calculateRace = false;
    private boolean calculateSex = false;


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

    public int getBlank() {
        return blank;
    }

    public void setBlank(int blank) {
        this.blank = blank;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getBrown() {
        return brown;
    }

    public void setBrown(int brown) {
        this.brown = brown;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getIndigenous() {
        return Indigenous;
    }

    public void setIndigenous(int indigenous) {
        Indigenous = indigenous;
    }

    public int getNoInformation() {
        return noInformation;
    }

    public void setNoInformation(int noInformation) {
        this.noInformation = noInformation;
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

    public boolean isCalculateRace() {
        return calculateRace;
    }

    public void setCalculateRace(boolean calculateRace) {
        this.calculateRace = calculateRace;
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
