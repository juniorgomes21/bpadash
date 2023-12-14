package br.com.bpadash.dto;

import java.util.ArrayList;
import java.util.List;

public class DatesDTO {
    private List<List<Integer>> dates = new ArrayList<>();

    public DatesDTO() {
    }

    public List<List<Integer>> getDates() {
        return dates;
    }

    public void setDates(List<List<Integer>> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "FpoDatesDTO{" +
                "dates=" + dates +
                '}';
    }
}
