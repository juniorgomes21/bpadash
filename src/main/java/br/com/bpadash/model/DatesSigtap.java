package br.com.bpadash.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DatesSigtap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOccupation;
    private LocalDate dateFpo;
    private LocalDate dateProfessionals;
    private LocalDate dateCep;
    private LocalDate dateProcedure;
    private boolean dateOccupationAuto = true;
    private boolean dateFpoAuto = true;
    private boolean dateProfessionalsAuto = true;
    private boolean dateCepAuto = true;
    private boolean dateProcedureAuto = true;
    @OneToOne
    private User user;

    public DatesSigtap() {
    }

    public DatesSigtap(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateOccupation() {
        return dateOccupation;
    }

    public void setDateOccupation(LocalDate dateOccupation) {
        this.dateOccupation = dateOccupation;
    }

    public LocalDate getDateFpo() {
        return dateFpo;
    }

    public void setDateFpo(LocalDate dateFpo) {
        this.dateFpo = dateFpo;
    }

    public LocalDate getDateProfessionals() {
        return dateProfessionals;
    }

    public void setDateProfessionals(LocalDate dateProfessionals) {
        this.dateProfessionals = dateProfessionals;
    }

    public LocalDate getDateCep() {
        return dateCep;
    }

    public void setDateCep(LocalDate dateCep) {
        this.dateCep = dateCep;
    }

    public LocalDate getDateProcedure() {
        return dateProcedure;
    }

    public void setDateProcedure(LocalDate dateProcedure) {
        this.dateProcedure = dateProcedure;
    }

    public boolean isDateOccupationAuto() {
        return dateOccupationAuto;
    }

    public void setDateOccupationAuto(boolean dateOccupationAuto) {
        this.dateOccupationAuto = dateOccupationAuto;
    }

    public boolean isDateFpoAuto() {
        return dateFpoAuto;
    }

    public void setDateFpoAuto(boolean dateFpoAuto) {
        this.dateFpoAuto = dateFpoAuto;
    }

    public boolean isDateProfessionalsAuto() {
        return dateProfessionalsAuto;
    }

    public void setDateProfessionalsAuto(boolean dateProfessionalsAuto) {
        this.dateProfessionalsAuto = dateProfessionalsAuto;
    }

    public boolean isDateCepAuto() {
        return dateCepAuto;
    }

    public void setDateCepAuto(boolean dateCepAuto) {
        this.dateCepAuto = dateCepAuto;
    }

    public boolean isDateProcedureAuto() {
        return dateProcedureAuto;
    }

    public void setDateProcedureAuto(boolean dateProcedureAuto) {
        this.dateProcedureAuto = dateProcedureAuto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
