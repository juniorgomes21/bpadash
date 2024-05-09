package br.com.bpadash.dto.sigtap;

import java.time.LocalDate;

public class DateDTO {
    private LocalDate date;

    public DateDTO() {
    }

    public DateDTO(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
