package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.*;
import br.com.produto.monkeyflip.model.enums.StatusVenda;
import br.com.produto.monkeyflip.repository.IMacaco;
import br.com.produto.monkeyflip.repository.IUsuario;
import br.com.produto.monkeyflip.repository.IVenda;
import br.com.produto.monkeyflip.repository.IVendaParcela;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private final IVenda repository;
    private final IVendaParcela repositoryParcela;
    private final IMacaco repositoryMacaco;
    private final IUsuario repositoryUsuario;


    @Autowired
    public VendaService(IVenda repository, IVendaParcela repositoryParcela, IMacaco repositoryMacaco, IUsuario repositoryUsuario) {
        this.repository = repository;
        this.repositoryParcela = repositoryParcela;
        this.repositoryMacaco = repositoryMacaco;
        this.repositoryUsuario = repositoryUsuario;
    }

    public Page<Venda> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }


    @Transactional
    public void salvar(Venda venda, List<VendaParcela> parcelas, String userId) {
        Optional<Macaco> macaco = repositoryMacaco.findById(venda.getMacacoId());

        if (macaco.isPresent() && macaco.get().getQuantidade() >= 1) {
            macaco.get().setQuantidade(macaco.get().getQuantidade() - 1);
            repositoryMacaco.save(macaco.get());
        } else {
            throw new Error("estoque negativo");
        }

        if (venda.getItensVenda() == null) {
            venda.setItensVenda(new ArrayList<>());
            venda.getItensVenda().add(gerarItensVenda(venda));
        }

        if (parcelas != null && !parcelas.isEmpty()) {
            venda.setParcelas(processarParcelas(parcelas, venda));
        }

        if (venda.getContasReceber() == null) {
            venda.setContasReceber(new ArrayList<>());

        }
        List<ContaReceber> contasReceber = processarContaReceber(venda);
        venda.getContasReceber().addAll(contasReceber);
        venda.setValorTotal(new BigDecimal(venda.getPrecoMacaco()));

        if (userId != null && !userId.isEmpty()) {
            venda.setUsuFinalizouVenda(repositoryUsuario.buscarNome(Long.valueOf(userId)));
        }
        venda.setStatusVenda(StatusVenda.FINALIZADA);


        repository.save(venda);
    }

    public List<ContaReceber> processarContaReceber(Venda venda) {
        List<ContaReceber> contasReceber = new ArrayList<>();
        LocalDate dataVencimento = venda.getData();

        if (venda.getParcelas() != null && !venda.getParcelas().isEmpty()) {
            for (VendaParcela parcela : venda.getParcelas()) {
                ContaReceber cr = new ContaReceber();
                cr.setDataVencimento(dataVencimento);
                cr.setVenda(venda);
                cr.setValor(parcela.getValorParcela());
                cr.setPago(false);
                parcela.setContaReceber(cr);

                contasReceber.add(cr);

                dataVencimento = dataVencimento.plusDays(30);
            }
        } else {
            ContaReceber cr = new ContaReceber();
            cr.setDataVencimento(dataVencimento);
            cr.setVenda(venda);
            cr.setValor(new BigDecimal(venda.getPrecoMacaco()));
            cr.setPago(false);
            contasReceber.add(cr);
        }

        return contasReceber;
    }


    public List<VendaParcela> processarParcelas(List<VendaParcela> parcelas, Venda venda) {
        for (VendaParcela parcela : parcelas) {
            parcela.setVenda(venda);
        }
        return parcelas;
    }

    public ItensVenda gerarItensVenda(Venda venda) {
        ItensVenda iv = new ItensVenda();
        iv.setVenda(venda);
        iv.setProduto(repositoryMacaco.getMacacoById(venda.getMacacoId()));
        iv.setQuantidade(1);
        iv.setPrecoUnitario(new BigDecimal(venda.getPrecoMacaco()));
        return iv;
    }


    public void excluir(Long id) {
        Venda venda = repository.findById(id).isPresent() ? repository.findById(id).get() : null;
        assert venda != null;

        Macaco macaco = repositoryMacaco.findById(venda.getMacacoId()).isPresent() ? repositoryMacaco.findById(venda.getMacacoId()).get() : null;
        assert macaco != null;
        macaco.setQuantidade(macaco.getQuantidade() + 1);

        repositoryMacaco.save(macaco);
        repository.deleteById(id);
    }

    public Venda buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Venda> buscarVendasPorId(Pageable pageable, String idpesquisa) {
        if (idpesquisa.isEmpty()) {
            return repository.findAllByOrderById(pageable);
        }
        return repository.buscarContasComIdVenda(pageable, Long.parseLong(idpesquisa));
    }
}
