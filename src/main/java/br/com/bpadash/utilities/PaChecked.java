package br.com.bpadash.utilities;

import java.util.Objects;

public class PaChecked {
    private String pa;
    private String msg;

    public PaChecked() {
    }

    public PaChecked(String pa) {
        this.pa = pa;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaChecked paChecked)) return false;
        return Objects.equals(getPa() , paChecked.getPa());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPa());
    }
}
