package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class ContaReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cr_valor")
    private BigDecimal valor;

    @Column(name = "cr_data_vencimento")
    private Date dataVencimento;

    @Column(name = "cr_pago")
    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @OneToMany(mappedBy = "contaReceber", cascade = CascadeType.ALL)
    private List<VendaParcela> parcelas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<VendaParcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<VendaParcela> parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaReceber that = (ContaReceber) o;
        return pago == that.pago && Objects.equals(id, that.id) && Objects.equals(valor, that.valor) && Objects.equals(dataVencimento, that.dataVencimento) && Objects.equals(venda, that.venda) && Objects.equals(parcelas, that.parcelas) && Objects.equals(venda, that.venda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, dataVencimento, pago, venda, parcelas, venda);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
