package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Macaco;
import br.com.produto.monkeyflip.model.Venda;
import br.com.produto.monkeyflip.repository.IVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private final IVenda repository;

    @Autowired
    public VendaService(IVenda repository) {
        this.repository = repository;
    }

    public Page<Venda> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }

    public void salvar(Venda venda) {
        repository.save(venda);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Venda buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
