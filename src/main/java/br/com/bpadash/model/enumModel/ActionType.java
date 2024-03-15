package br.com.bpadash.model.enumModel;

public enum ActionType {

    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    ADD("ADD"),
    ADD_BPAI("ADD_BPAI"),
    ADD_BPAC("ADD_BPAC"),
    DOWNLOAD("DOWNLOAD"),
    DELETE("DELETE"),
    DELETE_LINE("DELETE_LINE"),
    UPDATE("UPDATE"),
    UPDATE_PASSWORD("UPDATE_PASSWORD"),
    UPDATE_EMPLOYEE("UPDATE_EMPLOYEE");

    private final String action;

    ActionType(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
