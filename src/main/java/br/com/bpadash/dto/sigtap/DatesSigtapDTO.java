package br.com.bpadash.dto.sigtap;

import java.util.List;

public class DatesSigtapDTO {
    private Long id;
    private String arqName;
    private boolean auto;
    private List<List<List<Integer>>> dateCurrent;
    private List<Integer> years;
    private List<List<List<Integer>>> datesFull;

    public DatesSigtapDTO(Long id, String arqName , boolean auto , List<List<List<Integer>>> dateCurrent , List<Integer> years , List<List<List<Integer>>> datesFull) {
        this.id = id;
        this.arqName = arqName;
        this.auto = auto;
        this.dateCurrent = dateCurrent;
        this.years = years;
        this.datesFull = datesFull;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArqName() {
        return arqName;
    }

    public void setArqName(String arqName) {
        this.arqName = arqName;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public List<List<List<Integer>>> getDateCurrent() {
        return dateCurrent;
    }

    public void setDateCurrent(List<List<List<Integer>>> dateCurrent) {
        this.dateCurrent = dateCurrent;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<List<List<Integer>>> getDatesFull() {
        return datesFull;
    }

    public void setDatesFull(List<List<List<Integer>>> datesFull) {
        this.datesFull = datesFull;
    }
}
