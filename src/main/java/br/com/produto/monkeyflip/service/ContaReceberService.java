package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.ContaReceber;
import br.com.produto.monkeyflip.model.Venda;
import br.com.produto.monkeyflip.model.VendaParcela;
import br.com.produto.monkeyflip.repository.IContasReceber;
import br.com.produto.monkeyflip.repository.IVenda;
import br.com.produto.monkeyflip.repository.IVendaParcela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContaReceberService {


    private final IContasReceber repository;
    private final IVendaParcela parcelaRepository;
    private final IVenda vendaRepository;


    @Autowired
    public ContaReceberService(IContasReceber repository, IVendaParcela parcelaRepository, IVenda vendaRepository) {
        this.repository = repository;
        this.parcelaRepository = parcelaRepository;
        this.vendaRepository = vendaRepository;
    }

    public Page<ContaReceber> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }

    public ContaReceber buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void updateParaPago(Long id) {
        repository.atualizarStatusPago(id);
//        ContaReceber contaReceber = repository.findById(id).get();

        repository.findById(id).ifPresent(conta -> {
            conta.getVenda().getParcelas().forEach(parcela -> {
                if (parcela.getContaReceber().getId().equals(id)) {
                    parcela.setPago(true);
                    parcelaRepository.save(parcela);
                }
            });
            conta.setDataPagamento(LocalDate.now());
            repository.save(conta);
        });

    }

    public Page<ContaReceber> buscarContaPorVendaId(Pageable pageable,String idpesquisa) {
        if (idpesquisa.isEmpty()) {
            return repository.findAllByOrderById(pageable);
        }
        return repository.buscarContasComIdVenda(pageable, Long.parseLong(idpesquisa));
    }
}
