package br.com.bpadash.model.enumModel;

public enum ZoneTime {
    BR("America/Sao_Paulo");
    private String br;

    ZoneTime(String br) {
        this.br = br;
    }

    public String getBr() {
        return br;
    }
}
