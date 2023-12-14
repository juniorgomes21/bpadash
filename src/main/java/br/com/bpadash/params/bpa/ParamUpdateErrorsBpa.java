package br.com.bpadash.params.bpa;

import br.com.bpadash.validations.bpa.BpaValid;
import br.com.bpadash.validations.bpa.qtService.ValidQtService;
import br.com.bpadash.validations.bpa.race.RaceValid;
import br.com.bpadash.validations.bpa.sexo.SexoValid;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class ParamUpdateErrorsBpa {
    @BpaValid(size = 10, message = "PA deve ter 10 caracteres")
    private String pa;
    @Size(min = 8, max = 8, message = "Deve conter 10 caracteres")
    private String date;
    @Size(min = 8, max = 8, message = "Deve conter 10 caracteres")
    private String cep;
    @ValidQtService
    private List<Integer> qtService;
    @Size(min = 21, max = 21, message = "Deve conter 21 caracteres")
    private String dateBpaInvalid;
    @RaceValid(updateErrors = true)
    private String race;
    @Size(min = 33, max = 33, message = "Deve conter 33 caracteres")
    private String cnsmed;
    @SexoValid(updateErrors = true)
    private String sexCurrent;
    @Size(min = 6, max = 6, message = "Deve conter 6 caracteres")
    private String cbo;
    @Min(0)
    @Max(130)
    private Integer age;

    public ParamUpdateErrorsBpa() {
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<Integer> getQtService() {
        return qtService;
    }

    public void setQtService(List<Integer> qtService) {
        this.qtService = qtService;
    }

    public String getDateBpaInvalid() {
        return dateBpaInvalid;
    }

    public void setDateBpaInvalid(String dateBpaInvalid) {
        this.dateBpaInvalid = dateBpaInvalid;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCnsmed() {
        return cnsmed;
    }

    public void setCnsmed(String cnsmed) {
        this.cnsmed = cnsmed;
    }

    public String getSexCurrent() {
        return sexCurrent;
    }

    public void setSexCurrent(String sexCurrent) {
        this.sexCurrent = sexCurrent;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
