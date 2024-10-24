package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItensVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Macaco produto;

    @Column(name = "itv_quantidade")
    private int quantidade;

    @Column(name = "itv_preco_unitario")
    private BigDecimal precoUnitario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Macaco getProduto() {
        return produto;
    }

    public void setProduto(Macaco produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItensVenda that = (ItensVenda) o;
        return quantidade == that.quantidade && Objects.equals(id, that.id) && Objects.equals(venda, that.venda) && Objects.equals(produto, that.produto) && Objects.equals(precoUnitario, that.precoUnitario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venda, produto, quantidade, precoUnitario);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

