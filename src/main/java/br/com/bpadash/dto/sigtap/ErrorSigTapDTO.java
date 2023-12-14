package br.com.bpadash.dto.sigtap;

public class ErrorSigTapDTO {
    private Long id;
    private String msg;
    private String flh;
    private String seq;

    public ErrorSigTapDTO() {
    }

    public ErrorSigTapDTO(Long id , String msg , String flh , String seq) {
        this.id = id;
        this.msg = msg;
        this.flh = flh;
        this.seq = seq;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlh() {
        return flh;
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
