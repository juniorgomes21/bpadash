package br.com.bpadash.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Occupation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codProcedimento;
    private String codOcupacao;
    private String dtCompetencia;
    @ManyToOne
    private LinkOccupation linkOccupation;

    public Occupation() {
    }

    public Occupation(String codProcedimento , String codOcupacao , String dtCompetencia , LinkOccupation linkOccupation) {
        this.codProcedimento = codProcedimento;
        this.codOcupacao = codOcupacao;
        this.dtCompetencia = dtCompetencia;
        this.linkOccupation = linkOccupation;
    }

    public Long getId() {
        return id;
    }

    public String getCodProcedimento() {
        return codProcedimento;
    }

    public void setCodProcedimento(String codProcedimento) {
        this.codProcedimento = codProcedimento;
    }

    public String getCodOcupacao() {
        return codOcupacao;
    }

    public void setCodOcupacao(String codOcupacao) {
        this.codOcupacao = codOcupacao;
    }

    public String getDtCompetencia() {
        return dtCompetencia;
    }

    public void setDtCompetencia(String dtCompetencia) {
        this.dtCompetencia = dtCompetencia;
    }

    public LinkOccupation getLinkOccupation() {
        return linkOccupation;
    }

    public void setLinkOccupation(LinkOccupation linkOccupation) {
        this.linkOccupation = linkOccupation;
    }
}
