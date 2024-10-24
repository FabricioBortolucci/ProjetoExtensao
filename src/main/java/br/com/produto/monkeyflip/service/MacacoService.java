package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Macaco;
import br.com.produto.monkeyflip.repository.IMacaco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MacacoService {

    @Autowired
    private IMacaco repository;

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
}
