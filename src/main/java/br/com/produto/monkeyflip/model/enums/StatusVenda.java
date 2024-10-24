package br.com.produto.monkeyflip.model.enums;

public enum StatusVenda {

    CRIADA("Criada"),
    ANDAMENTO("Em Andamento"),
    FINALIZADA("Finalizada");


    public final String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }
}
