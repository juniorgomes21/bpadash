package br.com.bpadash.dto.graphics;

public class SexGraphicsDTO {
    private int countM;
    private int countF;

    public SexGraphicsDTO() {
    }

    public SexGraphicsDTO(int countM , int countF) {
        this.countM = countM;
        this.countF = countF;
    }

    public int getCountM() {
        return countM;
    }

    public void setCountM(int countM) {
        this.countM = countM;
    }

    public int getCountF() {
        return countF;
    }

    public void setCountF(int countF) {
        this.countF = countF;
    }
}
