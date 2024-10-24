package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class VendaParcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "vp_pago")
    private boolean pago;

    @Column(name = "vp_data_vencimento")
    private Date dataVencimento;

    @ManyToOne
    @JoinColumn(name = "conta_receber_id")
    private ContaReceber contaReceber;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaParcela that = (VendaParcela) o;
        return id == that.id && pago == that.pago && Objects.equals(valorParcela, that.valorParcela) && Objects.equals(dataVencimento, that.dataVencimento) && Objects.equals(contaReceber, that.contaReceber) && Objects.equals(venda, that.venda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valorParcela, pago, dataVencimento, contaReceber, venda);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
