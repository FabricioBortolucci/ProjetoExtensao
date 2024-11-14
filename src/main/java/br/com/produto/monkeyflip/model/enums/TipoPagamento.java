package br.com.produto.monkeyflip.model.enums;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TipoPagamento {

    PIX("Pix"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito");

    public final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public static List<TipoPagamento> buscarTiposPorPlano(PlanoPagamento planoPagamento) {
        return switch (planoPagamento) {
            case A -> Arrays.asList(PIX, CARTAO_DEBITO);
            case P -> Arrays.asList(CARTAO_CREDITO);
        };
    }

}
