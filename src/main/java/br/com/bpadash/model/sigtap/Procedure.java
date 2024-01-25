package br.com.bpadash.model.sigtap;

import br.com.bpadash.model.sigtap.LinkProcedure;

import javax.persistence.*;

@Entity
@Table(name = "procedures")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codProcedimento;
    @Column(name = "descricao", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String naProcedimento;
    private String tpComplexidade;
    private String tpSexo;
    private String qtMaximaExecucao;
    private String qtDiasPermanencia;
    private String qtPontos;
    private String vlIdadeMinima;
    private String vlIdadeMaxima;
    private String vlSh;
    private String vlSa;
    private String vlSp;
    private String codFinanceiro;
    private String codRubrica;
    private String qtTempoPermanencia;
    private String dtCompetencia;
    @ManyToOne
    private LinkProcedure linkProcedure;

    public Procedure() {
    }

    public Procedure(String codProcedimento , String naProcedimento , String tpComplexidade , String tpSexo , String qtMaximaExecucao , String qtDiasPermanencia , String qtPontos , String vlIdadeMinima , String vlIdadeMaxima , String vlSh , String vlSa , String vlSp , String codFinanceiro , String codRubrica , String qtTempoPermanencia , String dtCompetencia, LinkProcedure linkProcedure) {
        this.codProcedimento = codProcedimento;
        this.naProcedimento = naProcedimento;
        this.tpComplexidade = tpComplexidade;
        this.tpSexo = tpSexo;
        this.qtMaximaExecucao = qtMaximaExecucao;
        this.qtDiasPermanencia = qtDiasPermanencia;
        this.qtPontos = qtPontos;
        this.vlIdadeMinima = vlIdadeMinima;
        this.vlIdadeMaxima = vlIdadeMaxima;
        this.vlSh = vlSh;
        this.vlSa = vlSa;
        this.vlSp = vlSp;
        this.codFinanceiro = codFinanceiro;
        this.codRubrica = codRubrica;
        this.qtTempoPermanencia = qtTempoPermanencia;
        this.dtCompetencia = dtCompetencia;
        this.linkProcedure = linkProcedure;
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

    public String getNaProcedimento() {
        return naProcedimento;
    }

    public void setNaProcedimento(String naProcedimento) {
        this.naProcedimento = naProcedimento;
    }

    public String getTpComplexidade() {
        return tpComplexidade;
    }

    public void setTpComplexidade(String tpComplexidade) {
        this.tpComplexidade = tpComplexidade;
    }

    public String getTpSexo() {
        return tpSexo;
    }

    public void setTpSexo(String tpSexo) {
        this.tpSexo = tpSexo;
    }

    public String getQtMaximaExecucao() {
        return qtMaximaExecucao;
    }

    public void setQtMaximaExecucao(String qtMaximaExecucao) {
        this.qtMaximaExecucao = qtMaximaExecucao;
    }

    public String getQtDiasPermanencia() {
        return qtDiasPermanencia;
    }

    public void setQtDiasPermanencia(String qtDiasPermanencia) {
        this.qtDiasPermanencia = qtDiasPermanencia;
    }

    public String getQtPontos() {
        return qtPontos;
    }

    public void setQtPontos(String qtPontos) {
        this.qtPontos = qtPontos;
    }

    public String getVlIdadeMinima() {
        return vlIdadeMinima;
    }

    public void setVlIdadeMinima(String vlIdadeMinima) {
        this.vlIdadeMinima = vlIdadeMinima;
    }

    public String getVlIdadeMaxima() {
        return vlIdadeMaxima;
    }

    public void setVlIdadeMaxima(String vlIdadeMaxima) {
        this.vlIdadeMaxima = vlIdadeMaxima;
    }

    public String getVlSh() {
        return vlSh;
    }

    public void setVlSh(String vlSh) {
        this.vlSh = vlSh;
    }

    public String getVlSa() {
        return vlSa;
    }

    public void setVlSa(String vlSa) {
        this.vlSa = vlSa;
    }

    public String getVlSp() {
        return vlSp;
    }

    public void setVlSp(String vlSp) {
        this.vlSp = vlSp;
    }

    public String getCodFinanceiro() {
        return codFinanceiro;
    }

    public void setCodFinanceiro(String codFinanceiro) {
        this.codFinanceiro = codFinanceiro;
    }

    public String getCodRubrica() {
        return codRubrica;
    }

    public void setCodRubrica(String codRubrica) {
        this.codRubrica = codRubrica;
    }

    public String getQtTempoPermanencia() {
        return qtTempoPermanencia;
    }

    public void setQtTempoPermanencia(String qtTempoPermanencia) {
        this.qtTempoPermanencia = qtTempoPermanencia;
    }

    public LinkProcedure getLinkProcedure() {
        return linkProcedure;
    }

    public void setLinkProcedure(LinkProcedure linkProcedure) {
        this.linkProcedure = linkProcedure;
    }

    public String getDtCompetencia() {
        return dtCompetencia;
    }

    public void setDtCompetencia(String dtCompetencia) {
        this.dtCompetencia = dtCompetencia;
    }
}
