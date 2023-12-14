package br.com.bpadash.dto.sigtap;

public class ErrorCnsmedDTO extends ErrorSigTapDTO {

    private String pa;
    private String cnsmed;
    private long count;

    public ErrorCnsmedDTO(Long id, String flh, String seq, String msg, String pa, String cnsmed, long count) {
        super(id , msg, flh, seq);
        this.pa = pa;
        this.cnsmed = cnsmed;
        this.count = count;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getCnsmed() {
        return cnsmed;
    }

    public void setCnsmed(String cnsmed) {
        this.cnsmed = cnsmed;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
