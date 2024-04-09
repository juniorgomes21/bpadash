package br.com.bpadash.model.email;

public class CodeVerify {

    public static String createTitle(String userName) {
        return "Código de verificação SuaSorte!";
    }

    public static String createContent(String code) {
        StringBuilder codeVerify = new StringBuilder(code);
        codeVerify.insert(3, "-");

        return "Este é seu código de verificação " + code + ".";
    }
}
