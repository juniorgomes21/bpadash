package br.com.bpadash.params.bpa;

public class ParamValidationTitle {
    private boolean lin;
    private boolean flh;
    private boolean smtVrf;
    private boolean cgccpf;


    public ParamValidationTitle() {
    }

    public boolean isLin() {
        return lin;
    }

    public boolean isFlh() {
        return flh;
    }

    public void setFlh(boolean flh) {
        this.flh = flh;
    }

    public void setLin(boolean lin) {
        this.lin = lin;
    }

    public boolean isSmtVrf() {
        return smtVrf;
    }

    public void setSmtVrf(boolean smtVrf) {
        this.smtVrf = smtVrf;
    }

    public boolean isCgccpf() {
        return cgccpf;
    }

    public void setCgccpf(boolean cgccpf) {
        this.cgccpf = cgccpf;
    }
}
