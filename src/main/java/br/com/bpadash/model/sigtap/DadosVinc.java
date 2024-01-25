package br.com.bpadash.model.sigtap;

import javax.persistence.*;

@Entity
public class DadosVinc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codCbo;
    private String indVinc;
    private String cghoraoutr;
    private String cgHoraamb;
    private String conselhoid;
    private String numberRegister;
    private String vinSus;
    private String user;
    private String cghorahosp;

    public DadosVinc() {
    }

    public DadosVinc(String codCbo , String indVinc , String cghoraoutr , String cgHoraamb , String conselhoid , String numberRegister , String vinSus , String user , String cghorahosp) {
        this.codCbo = codCbo;
        this.indVinc = indVinc;
        this.cghoraoutr = cghoraoutr;
        this.cgHoraamb = cgHoraamb;
        this.conselhoid = conselhoid;
        this.numberRegister = numberRegister;
        this.vinSus = vinSus;
        this.user = user;
        this.cghorahosp = cghorahosp;
    }

    public Long getId() {
        return id;
    }

    public String getCodCbo() {
        return codCbo;
    }

    public void setCodCbo(String codCbo) {
        this.codCbo = codCbo;
    }

    public String getIndVinc() {
        return indVinc;
    }

    public void setIndVinc(String indVinc) {
        this.indVinc = indVinc;
    }

    public String getCghoraoutr() {
        return cghoraoutr;
    }

    public void setCghoraoutr(String cghoraoutr) {
        this.cghoraoutr = cghoraoutr;
    }

    public String getCgHoraamb() {
        return cgHoraamb;
    }

    public void setCgHoraamb(String cgHoraamb) {
        this.cgHoraamb = cgHoraamb;
    }

    public String getConselhoid() {
        return conselhoid;
    }

    public void setConselhoid(String conselhoid) {
        this.conselhoid = conselhoid;
    }

    public String getNumberRegister() {
        return numberRegister;
    }

    public void setNumberRegister(String numberRegister) {
        this.numberRegister = numberRegister;
    }

    public String getVinSus() {
        return vinSus;
    }

    public void setVinSus(String vinSus) {
        this.vinSus = vinSus;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCghorahosp() {
        return cghorahosp;
    }

    public void setCghorahosp(String cghorahosp) {
        this.cghorahosp = cghorahosp;
    }

    @Override
    public String toString() {
        return  codCbo +
                indVinc +
                cghoraoutr +
                cgHoraamb +
                conselhoid +
                numberRegister +
                vinSus +
                user +
                cghorahosp;
    }
}
