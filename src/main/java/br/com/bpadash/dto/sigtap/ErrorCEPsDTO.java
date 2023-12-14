package br.com.bpadash.dto.sigtap;

public class ErrorCEPsDTO extends ErrorSigTapDTO{
    public String cepInvalid;

    public ErrorCEPsDTO() {
    }

    public ErrorCEPsDTO(Long id, String flh, String seq, String msg ,String cepInvalid) {
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
