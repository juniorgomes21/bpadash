package br.com.bpadash.dto.sigtap;

public class ErrorQtMaxDTODTO extends ErrorSigTapDTO {
    private String name;
    private int qt;
    private int qtMax;
    private String pa;

    public ErrorQtMaxDTODTO(Long id, String flh, String seq, String msg, String name, int qt, int qtMax, String pa) {
        super(id , msg, flh, seq);
        this.name = name;
        this.qt = qt;
        this.qtMax = qtMax;
        this.pa = pa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    public int getQtMax() {
        return qtMax;
    }

    public void setQtMax(int qtMax) {
        this.qtMax = qtMax;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }
}
