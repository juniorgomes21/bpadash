package br.com.bpadash.dto.sigtap;

public class ErrorRaceDTO extends ErrorSigTapDTO {
    private String name;
    private String raceInvalid;

    public ErrorRaceDTO(Long id, String flh, String seq , String msg , String name , String raceInvalid) {
        super(id , msg, flh, seq);
        this.name = name;
        this.raceInvalid = raceInvalid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaceInvalid() {
        return raceInvalid;
    }

    public void setRaceInvalid(String raceInvalid) {
        this.raceInvalid = raceInvalid;
    }
}
