package br.com.bpadash.dto.sigtap;

public class ErrorCEPsInvalidsDTO extends ErrorSigTapDTO{
    public String cepInvalid;

    public ErrorCEPsInvalidsDTO() {
    }

    public ErrorCEPsInvalidsDTO(Long id, String flh, String seq, String msg , String cepInvalid) {
        super(id , msg , flh , seq);
        this.cepInvalid = cepInvalid;
    }


    public String getCepInvalid() {
        return cepInvalid;
    }

    public void setCepInvalid(String cepInvalid) {
        this.cepInvalid = cepInvalid;
    }
}
