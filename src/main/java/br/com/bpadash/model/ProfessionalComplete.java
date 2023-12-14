package br.com.bpadash.model;

import javax.persistence.*;

@Entity
public class ProfessionalComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profId;
    private String keyProfId;
    private String cpf;
    private String pispasep;
    private String name;
    private String nameMother;
    private String birthDate;
    private String codMun;
    private String sexo;
    private String numBook;
    private String numSheet;
    private String numTerm;
    private String codorgemis;
    private String dateEmiss;
    private String numIdent;
    private String siglaEst;
    private String dtemiident;
    private String dateEntra;
    private String ctpsNumer;
    private String serie;
    private String sigestctps;
    private String dtemisctps;
    private String logradouro;
    private String number;
    private String complement;
    private String bairrodist;
    private String codCep;
    private String siglaUf;
    private String codEscolar;
    private String codCertid;
    private String indNacio;
    private String nameCarto;
    private String codBanc;
    private String nameCountry;
    private String numAgenc;
    private String contaCc;
    private String codCns;
    private String dTercsih;
    private String status;
    private String statusmov;
    private String date;
    private String user;
    private String cdRaca;
    private String telephone;
    private String nameFather;
    private String cdTpLogr;
    private String portaria;
    private String dtNatur;
    private String codCountry;
    @OneToOne(cascade = CascadeType.ALL)
    private DadosVinc dadosVinc;
    @ManyToOne
    private LinkProfessionals linkProfessionals;

    public ProfessionalComplete() {
    }

    public ProfessionalComplete(LinkProfessionals linkProfessionals , String profId , String cpf , String pispasep , String name , String nameMother , String birthDate , String codMun , String sexo , String numBook , String numSheet , String numTerm , String codorgemis , String dateEmiss , String numIdent , String siglaEst , String dtemiident , String dateEntra , String ctpsNumer , String serie , String sigestctps , String dtemisctps , String logradouro , String number , String complement , String bairrodist , String codCep , String siglaUf , String codEscolar , String codCertid , String indNacio , String nameCarto , String codBanc , String nameCountry , String numAgenc , String contaCc , String codCns , String dTercsih , String status , String statusmov , String date , String user , String cdRaca , String telephone , String nameFather , String cdTpLogr , String portaria , String dtNatur , String codCountry) {
        this.linkProfessionals = linkProfessionals;
        this.profId = profId;
        this.keyProfId = profId;
        this.cpf = cpf;
        this.pispasep = pispasep;
        this.name = name;
        this.nameMother = nameMother;
        this.birthDate = birthDate;
        this.codMun = codMun;
        this.sexo = sexo;
        this.numBook = numBook;
        this.numSheet = numSheet;
        this.numTerm = numTerm;
        this.codorgemis = codorgemis;
        this.dateEmiss = dateEmiss;
        this.numIdent = numIdent;
        this.siglaEst = siglaEst;
        this.dtemiident = dtemiident;
        this.dateEntra = dateEntra;
        this.ctpsNumer = ctpsNumer;
        this.serie = serie;
        this.sigestctps = sigestctps;
        this.dtemisctps = dtemisctps;
        this.logradouro = logradouro;
        this.number = number;
        this.complement = complement;
        this.bairrodist = bairrodist;
        this.codCep = codCep;
        this.siglaUf = siglaUf;
        this.codEscolar = codEscolar;
        this.codCertid = codCertid;
        this.indNacio = indNacio;
        this.nameCarto = nameCarto;
        this.codBanc = codBanc;
        this.nameCountry = nameCountry;
        this.numAgenc = numAgenc;
        this.contaCc = contaCc;
        this.codCns = codCns;
        this.dTercsih = dTercsih;
        this.status = status;
        this.statusmov = statusmov;
        this.date = date;
        this.user = user;
        this.cdRaca = cdRaca;
        this.telephone = telephone;
        this.nameFather = nameFather;
        this.cdTpLogr = cdTpLogr;
        this.portaria = portaria;
        this.dtNatur = dtNatur;
        this.codCountry = codCountry;
    }

    public Long getId() {
        return id;
    }

    public String getProfId() {
        return profId;
    }

    public String getKeyProfId() {
        return keyProfId;
    }

    public void setKeyProfId(String keyProfId) {
        this.keyProfId = keyProfId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPispasep() {
        return pispasep;
    }

    public void setPispasep(String pispasep) {
        this.pispasep = pispasep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMother() {
        return nameMother;
    }

    public void setNameMother(String nameMother) {
        this.nameMother = nameMother;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCodMun() {
        return codMun;
    }

    public void setCodMun(String codMun) {
        this.codMun = codMun;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumBook() {
        return numBook;
    }

    public void setNumBook(String numBook) {
        this.numBook = numBook;
    }

    public String getNumSheet() {
        return numSheet;
    }

    public void setNumSheet(String numSheet) {
        this.numSheet = numSheet;
    }

    public String getNumTerm() {
        return numTerm;
    }

    public void setNumTerm(String numTerm) {
        this.numTerm = numTerm;
    }

    public String getCodorgemis() {
        return codorgemis;
    }

    public void setCodorgemis(String codorgemis) {
        this.codorgemis = codorgemis;
    }

    public String getDateEmiss() {
        return dateEmiss;
    }

    public void setDateEmiss(String dateEmiss) {
        this.dateEmiss = dateEmiss;
    }

    public String getNumIdent() {
        return numIdent;
    }

    public void setNumIdent(String numIdent) {
        this.numIdent = numIdent;
    }

    public String getSiglaEst() {
        return siglaEst;
    }

    public void setSiglaEst(String siglaEst) {
        this.siglaEst = siglaEst;
    }

    public String getDtemiident() {
        return dtemiident;
    }

    public void setDtemiident(String dtemiident) {
        this.dtemiident = dtemiident;
    }

    public String getDateEntra() {
        return dateEntra;
    }

    public void setDateEntra(String dateEntra) {
        this.dateEntra = dateEntra;
    }

    public String getCtpsNumer() {
        return ctpsNumer;
    }

    public void setCtpsNumer(String ctpsNumer) {
        this.ctpsNumer = ctpsNumer;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getSigestctps() {
        return sigestctps;
    }

    public void setSigestctps(String sigestctps) {
        this.sigestctps = sigestctps;
    }

    public String getDtemisctps() {
        return dtemisctps;
    }

    public void setDtemisctps(String dtemisctps) {
        this.dtemisctps = dtemisctps;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getBairrodist() {
        return bairrodist;
    }

    public void setBairrodist(String bairrodist) {
        this.bairrodist = bairrodist;
    }

    public String getCodCep() {
        return codCep;
    }

    public void setCodCep(String codCep) {
        this.codCep = codCep;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    public String getCodEscolar() {
        return codEscolar;
    }

    public void setCodEscolar(String codEscolar) {
        this.codEscolar = codEscolar;
    }

    public String getCodCertid() {
        return codCertid;
    }

    public void setCodCertid(String codCertid) {
        this.codCertid = codCertid;
    }

    public String getIndNacio() {
        return indNacio;
    }

    public void setIndNacio(String indNacio) {
        this.indNacio = indNacio;
    }

    public String getNameCarto() {
        return nameCarto;
    }

    public void setNameCarto(String nameCarto) {
        this.nameCarto = nameCarto;
    }

    public String getCodBanc() {
        return codBanc;
    }

    public void setCodBanc(String codBanc) {
        this.codBanc = codBanc;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getNumAgenc() {
        return numAgenc;
    }

    public void setNumAgenc(String numAgenc) {
        this.numAgenc = numAgenc;
    }

    public String getContaCc() {
        return contaCc;
    }

    public void setContaCc(String contaCc) {
        this.contaCc = contaCc;
    }

    public String getCodCns() {
        return codCns;
    }

    public void setCodCns(String codCns) {
        this.codCns = codCns;
    }

    public String getdTercsih() {
        return dTercsih;
    }

    public void setdTercsih(String dTercsih) {
        this.dTercsih = dTercsih;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusmov() {
        return statusmov;
    }

    public void setStatusmov(String statusmov) {
        this.statusmov = statusmov;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCdRaca() {
        return cdRaca;
    }

    public void setCdRaca(String cdRaca) {
        this.cdRaca = cdRaca;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNameFather() {
        return nameFather;
    }

    public void setNameFather(String nameFather) {
        this.nameFather = nameFather;
    }

    public String getCdTpLogr() {
        return cdTpLogr;
    }

    public void setCdTpLogr(String cdTpLogr) {
        this.cdTpLogr = cdTpLogr;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }

    public String getDtNatur() {
        return dtNatur;
    }

    public void setDtNatur(String dtNatur) {
        this.dtNatur = dtNatur;
    }

    public String getCodCountry() {
        return codCountry;
    }

    public void setCodCountry(String codCountry) {
        this.codCountry = codCountry;
    }

    public DadosVinc getDadosVinc() {
        return dadosVinc;
    }

    public void setDadosVinc(DadosVinc dadosVinc) {
        this.dadosVinc = dadosVinc;
    }

    public LinkProfessionals getMainProfessionals() {
        return linkProfessionals;
    }

    public void setMainProfessionals(LinkProfessionals linkProfessionals) {
        this.linkProfessionals = linkProfessionals;
    }

    @Override
    public String toString() {
        return profId +
                cpf +
                pispasep +
                name +
                nameMother +
                birthDate +
                codMun +
                sexo +
                numBook +
                numSheet +
                numTerm +
                codorgemis +
                dateEmiss +
                numIdent +
                siglaEst +
                dtemiident +
                dateEntra +
                ctpsNumer +
                serie +
                sigestctps +
                dtemisctps +
                logradouro +
                number +
                complement +
                bairrodist +
                codCep +
                siglaUf +
                codEscolar +
                codCertid +
                indNacio +
                nameCarto +
                codBanc +
                nameCountry +
                numAgenc +
                contaCc +
                codCns +
                dTercsih +
                status +
                statusmov +
                date +
                user +
                cdRaca +
                telephone +
                nameFather +
                cdTpLogr +
                portaria +
                dtNatur +
                codCountry;
    }
}
