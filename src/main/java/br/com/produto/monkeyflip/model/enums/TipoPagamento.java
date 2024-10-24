package br.com.produto.monkeyflip.model.enums;


public enum TipoPagamento {

    PIX("Pix"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito");

    public final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }
}
