package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimento;

    @Column(name = "vp_numparcela")
    private int numparcela;

    @ManyToOne
    @JoinColumn(name = "conta_receber_id")
    private ContaReceber contaReceber;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    public VendaParcela(BigDecimal valorParcela, boolean pago, LocalDate dataVencimento, Venda venda, int numparcela) {
        this.valorParcela = valorParcela;
        this.pago = pago;
        this.dataVencimento = dataVencimento;
        this.venda = venda;
        this.numparcela = numparcela;
    }

    public VendaParcela() {

    }

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

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
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

    public int getNumparcela() {
        return numparcela;
    }

    public void setNumparcela(int numparcela) {
        this.numparcela = numparcela;
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
