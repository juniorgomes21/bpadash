package br.com.bpadash.dto.sigtap;

public class ErrorDtAtendDTODTO extends ErrorSigTapDTO {
    private String dateBpa;
    private String dateInvalid;

    public ErrorDtAtendDTODTO(Long id, String flh, String seq, String msg , String dateBpa , String dateInvalid) {
        super(id , msg, flh, seq);
        this.dateBpa = dateBpa;
        this.dateInvalid = dateInvalid;
    }

    public String getDateBpa() {
        return dateBpa;
    }

    public void setDateBpa(String dateBpa) {
        this.dateBpa = dateBpa;
    }

    public String getDateInvalid() {
        return dateInvalid;
    }

    public void setDateInvalid(String dateInvalid) {
        this.dateInvalid = dateInvalid;
    }
}
