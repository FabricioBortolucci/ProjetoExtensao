package br.com.produto.monkeyflip.model;

import br.com.produto.monkeyflip.model.enums.PlanoPagamento;
import br.com.produto.monkeyflip.model.enums.StatusVenda;
import br.com.produto.monkeyflip.model.enums.TipoPagamento;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_venda")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItensVenda> itensVenda;

    @Enumerated(EnumType.STRING)
    @Column(name = "ven_tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ven_plano_pagamento")
    private PlanoPagamento planoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ven_status")
    private StatusVenda statusVenda;

    @Column(name = "ven_valor_total")
    private BigDecimal valorTotal;

    @Column(name = "ven_usu_finalizouu_venda")
    private String usuFinalizouVenda;

    @Column(name = "ven_quant_parcelas")
    private Integer quantParcelas;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContaReceber> contasReceber;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaParcela> parcelas;


    private Long macacoId; // ID do macaco
    private String precoMacaco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItensVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItensVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public PlanoPagamento getPlanoPagamento() {
        return planoPagamento;
    }

    public void setPlanoPagamento(PlanoPagamento planoPagamento) {
        this.planoPagamento = planoPagamento;
    }

    public String getUsuFinalizouVenda() {
        return usuFinalizouVenda;
    }

    public void setUsuFinalizouVenda(String usuFinalizouVenda) {
        this.usuFinalizouVenda = usuFinalizouVenda;
    }

    public StatusVenda getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(StatusVenda statusVenda) {
        this.statusVenda = statusVenda;
    }

    public Integer getQuantParcelas() {
        return quantParcelas;
    }

    public void setQuantParcelas(Integer quantParcelas) {
        this.quantParcelas = quantParcelas;
    }

    public List<ContaReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContaReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

    public List<VendaParcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<VendaParcela> parcelas) {
        this.parcelas = parcelas;
    }

    public Long getMacacoId() {
        return macacoId;
    }

    public void setMacacoId(Long macacoId) {
        this.macacoId = macacoId;
    }

    public String getPrecoMacaco() {
        return precoMacaco;
    }

    public void setPrecoMacaco(String precoMacaco) {
        this.precoMacaco = precoMacaco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id) && Objects.equals(data, venda.data) && Objects.equals(itensVenda, venda.itensVenda) && Objects.equals(valorTotal, venda.valorTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, itensVenda, valorTotal);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

