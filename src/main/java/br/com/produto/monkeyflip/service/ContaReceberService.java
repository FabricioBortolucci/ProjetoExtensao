package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.ContaReceber;
import br.com.produto.monkeyflip.repository.IContasReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContaReceberService {


    private final IContasReceber repository;

    @Autowired
    public ContaReceberService(IContasReceber repository) {
        this.repository = repository;
    }

    public Page<ContaReceber> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }

    public void updateParaPago(Long id) {
        repository.atualizarStatusPago(id);
        repository.findById(id).ifPresent(conta -> {
            conta.setDataPagamento(LocalDate.now());
            repository.save(conta);
        });
    }
}
