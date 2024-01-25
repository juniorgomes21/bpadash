package br.com.bpadash.dto.professional;

public class CountProfessionalDTO {
    private int profissionalNumberFree;
    private int totalProfissional;

    public CountProfessionalDTO() {
    }


    public int getProfissionalNumberFree() {
        return profissionalNumberFree;
    }

    public void setProfissionalNumberFree(int profissionalNumberFree) {
        this.profissionalNumberFree = profissionalNumberFree;
    }

    public int getTotalProfissional() {
        return totalProfissional;
    }

    public void setTotalProfissional(int totalProfissional) {
        this.totalProfissional = totalProfissional;
    }
}
