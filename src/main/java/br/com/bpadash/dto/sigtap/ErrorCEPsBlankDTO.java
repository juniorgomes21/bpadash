package br.com.bpadash.dto.sigtap;

public class ErrorCEPsBlankDTO extends ErrorCEPsInvalidsDTO {

    public ErrorCEPsBlankDTO() {
    }

    public ErrorCEPsBlankDTO(Long id , String flh , String seq , String msg , String cepInvalid) {
        super(id , flh , seq , msg , cepInvalid);
    }
}
