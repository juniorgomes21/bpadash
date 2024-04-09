package br.com.bpadash.model.email;

public class MessageModel {

    public static String createTitle(String userName) {
        return "Olá " + userName + " !";
    }

    public static String codeConfirm(String code) {

        String msg = "Para continuar com seu login, ";

        msg = msg + " copie o código ( " + code + " ) e acesse a sua conta. Se não fez a solicitação, ignore esta mensagem. \n" +
                "Ateciosamente\n" +
                "\n" +
                "Equipe BPADASH.";

        return msg;
    }


    public static String codeTestEmail(String code) {

        String msg = "Para confirma seu email, ";

        msg = msg + " informe esse código ( " + code + " ) a um de nossos administradores. Se não fez a solicitação, ignore esta mensagem. \n" +
                "Ateciosamente\n" +
                "\n" +
                "Equipe BPADASH.";

        return msg;
    }
}
