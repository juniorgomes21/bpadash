package br.com.bpadash.model.enumModel;

public enum ActionEmployee {

    LOGIN("Usuário fez login na aplicação"),
    LOGOUT("Usuário saiu da aplicação"),
    UPDATE_TITLE("Título atualizado"),
    UPDATE_BPAI("Linha BPAI atualizada"),
    DELETE_BPA("Arquivo BPA excluído"),
    DELETE_BPAI("Linha BPAI excluída"),
    DELETE_FPO("Arquivo FPO excluído"),
    ADD_BPA("Novo arquivo BPA"),
    ADD_PROF("Novo arquivo de Profissionais"),
    ADD_BPAI("Novas linhas BPAI"),
    ADD_BPAC("Novas linhas BPAC"),
    ADD_FPO("Novo arquivo FPO"),
    DELETE_PROF("Arquivo Profissionais excluído"),
    DOWNLOAD("Arquivo BPA baixado"),
    UPDATE_BPA("Linhas BPA atualizadas"),
    UPDATE_BPAC("Linha BPAC atualizada"),
    UPDATE_ACCOUNT_MASTER("O usuário root alterou um registro"),
    UPDATE_PASSWORD("Senha da conta principal alterada"),
    UPDATE_ACCOUNT("O usuário alterou o registro"),
    DELETE_BPAC("Linha BPAC excluída"),
    UPDATE_RULE_PA("Substituição de PA em BPA"),
    UPDATE_RULE_CEP_BLANK("Substituição de CEP em branco no arquivo BPAI"),
    UPDATE_RULE_PA_CBO("Substituição de PA e CBO em BPA"),
    DELETE_PER_PA("Linhas excluídas em BPA");

    private final String action;

    ActionEmployee(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
