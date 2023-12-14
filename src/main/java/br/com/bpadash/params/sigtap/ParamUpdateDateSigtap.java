package br.com.bpadash.params.sigtap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public class ParamUpdateDateSigtap {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    private String arqName;
    @NotNull
    private boolean auto;
    @NotNull
    private List<List<List<Object>>> dateCurrent;

    public ParamUpdateDateSigtap() {
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

    public List<List<List<Object>>> getDateCurrent() {
        return dateCurrent;
    }

    public LocalDate getLocalDateCurrent() {
        int year;
        try {
            year = (int) dateCurrent.get(0).get(1).get(0);
        } catch (Exception e) {
            return null;
        }

        int month;
        try {
           month = (int) dateCurrent.get(0).get(0).get(0);
        } catch (Exception e) {
            return null;
        }

        return LocalDate.of(year, month, 1);
    }

    public void setDateCurrent(List<List<List<Object>>> dateCurrent) {
        this.dateCurrent = dateCurrent;
    }
}
