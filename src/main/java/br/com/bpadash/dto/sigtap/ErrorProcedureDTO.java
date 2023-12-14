package br.com.bpadash.dto.sigtap;

public class ErrorProcedureDTO extends ErrorSigTapDTO {
    private String pa;
    private String sexInvalid;
    private String sexCurrent;
    private String type;

    public ErrorProcedureDTO(Long id, String flh, String seq, String msg, String pa, String sexInvalid , String sexCurrent, String type) {
        super(id , msg, flh, seq);
        this.pa = pa;
        this.sexInvalid = sexInvalid;
        this.sexCurrent = sexCurrent;
        this.type = type;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getSexInvalid() {
        return sexInvalid;
    }

    public void setSexInvalid(String sexInvalid) {
        this.sexInvalid = sexInvalid;
    }

    public String getSexCurrent() {
        return sexCurrent;
    }

    public void setSexCurrent(String sexCurrent) {
        this.sexCurrent = sexCurrent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
