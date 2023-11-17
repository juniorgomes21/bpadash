package br.com.bpadash.dto.fpo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FpoDatesDTO {
    private List<List<Integer>> dates = new ArrayList<>();

    public FpoDatesDTO() {
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
