package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.model.Macaco;
import br.com.produto.monkeyflip.repository.IItensVenda;
import br.com.produto.monkeyflip.repository.IMacaco;
import br.com.produto.monkeyflip.repository.IVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MacacoService {

    @Autowired
    private IMacaco repository;

    @Autowired
    private IItensVenda repositoryItensVenda;

    public Page<Macaco> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }

    public void salvar(Macaco macaco) {
        repository.save(macaco);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Macaco buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Macaco> buscarPorNome(String termo) {
        return repository.findByNomeContainingIgnoreCase(termo)
                .stream()
                .map(m -> new Macaco(m.getId(), m.getNome(), m.getPrecoUnitario(), m.getQuantidade()))
                .collect(Collectors.toList());
    }

    public boolean macacoRelacionadoComVenda(Long id) {
        return repositoryItensVenda.existsByProduto_Id(id);
    }

}
