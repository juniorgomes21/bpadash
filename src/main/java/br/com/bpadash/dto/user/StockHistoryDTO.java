package br.com.bpadash.dto.user;

import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.StockHistory;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class StockHistoryDTO {
    private String type;
    private String action;
    private LocalDate dateFile;
    private LocalDateTime date;
    private String employee;
    private int linesModified;


    public StockHistoryDTO(StockHistory stockHistory) {
        this.type = stockHistory.getType();
        this.action = stockHistory.getAction();
        this.dateFile = stockHistory.getDateFile();
        this.date = stockHistory.getDate();
        this.employee = stockHistory.getEmployee().getName();
        this.linesModified = stockHistory.getLinesModified();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getDateFile() {
        return dateFile;
    }

    public void setDateFile(LocalDate dateFile) {
        this.dateFile = dateFile;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public int getLinesModified() {
        return linesModified;
    }

    public void setLinesModified(int linesModified) {
        this.linesModified = linesModified;
    }
}
