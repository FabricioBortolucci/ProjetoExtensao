package br.com.produto.monkeyflip.model.enums;


public enum PlanoPagamento {

    A("Á Vista"),
    P("Á Prazo");

    public final String descricao;

    PlanoPagamento(String descricao) {
        this.descricao = descricao;
    }
}
