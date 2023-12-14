package br.com.bpadash.dto.sigtap;

public class ErrorPaDTO extends ErrorSigTapDTO{
    public String type;
    public String pa;

    public ErrorPaDTO(Long id, String flh, String seq, String msg , String pa, String type) {
        super(id , msg, flh, seq);
        this.pa = pa;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }
}
