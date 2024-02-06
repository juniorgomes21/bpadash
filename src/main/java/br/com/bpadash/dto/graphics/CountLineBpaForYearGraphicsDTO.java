package br.com.bpadash.dto.graphics;

import java.util.List;

public class CountLineBpaForYearGraphicsDTO {
    private int countJan = 0;
    private int countFev = 0;
    private int countMar = 0;
    private int countAbr = 0;
    private int countMai = 0;
    private int countJun = 0;
    private int countJul = 0;
    private int countAgo = 0;
    private int countSet = 0;
    private int countOut = 0;
    private int countNov = 0;
    private int countDez = 0;


    public CountLineBpaForYearGraphicsDTO() {
    }

    public CountLineBpaForYearGraphicsDTO(List<Integer> valeuTotalInvoicing) {
        this.countJan = valeuTotalInvoicing.get(0);
        this.countFev = valeuTotalInvoicing.get(1);
//        this.countMar = valeuTotalInvoicing.get(2);
//        this.countAbr = valeuTotalInvoicing.get(3);
//        this.countMai = valeuTotalInvoicing.get(4);
//        this.countJun = valeuTotalInvoicing.get(5);
//        this.countJul = valeuTotalInvoicing.get(6);
//        this.countAgo = valeuTotalInvoicing.get(7);
//        this.countSet = valeuTotalInvoicing.get(8);
//        this.countOut = valeuTotalInvoicing.get(9);
//        this.countNov = valeuTotalInvoicing.get(10);
//        this.countDez = valeuTotalInvoicing.get(11);
    }

    public int getCountJan() {
        return countJan;
    }

    public void setCountJan(int countJan) {
        this.countJan = countJan;
    }

    public int getCountFev() {
        return countFev;
    }

    public void setCountFev(int countFev) {
        this.countFev = countFev;
    }

    public int getCountMar() {
        return countMar;
    }

    public void setCountMar(int countMar) {
        this.countMar = countMar;
    }

    public int getCountAbr() {
        return countAbr;
    }

    public void setCountAbr(int countAbr) {
        this.countAbr = countAbr;
    }

    public int getCountMai() {
        return countMai;
    }

    public void setCountMai(int countMai) {
        this.countMai = countMai;
    }

    public int getCountJun() {
        return countJun;
    }

    public void setCountJun(int countJun) {
        this.countJun = countJun;
    }

    public int getCountJul() {
        return countJul;
    }

    public void setCountJul(int countJul) {
        this.countJul = countJul;
    }

    public int getCountAgo() {
        return countAgo;
    }

    public void setCountAgo(int countAgo) {
        this.countAgo = countAgo;
    }

    public int getCountSet() {
        return countSet;
    }

    public void setCountSet(int countSet) {
        this.countSet = countSet;
    }

    public int getCountOut() {
        return countOut;
    }

    public void setCountOut(int countOut) {
        this.countOut = countOut;
    }

    public int getCountNov() {
        return countNov;
    }

    public void setCountNov(int countNov) {
        this.countNov = countNov;
    }

    public int getCountDez() {
        return countDez;
    }

    public void setCountDez(int countDez) {
        this.countDez = countDez;
    }

}
