package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.repository.IFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private IFuncionario repository;


    public Page<Funcionario> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }

    public void salvar(Funcionario funcionario) {
        String cpfSemMascara = funcionario.getCpf().replaceAll("\\D", "");  // Remove qualquer caractere que não seja dígito
        String telefoneSemMascara = funcionario.getTelefone().replaceAll("\\D", "");

        funcionario.setCpf(cpfSemMascara);
        funcionario.setTelefone(telefoneSemMascara);

        repository.save(funcionario);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Funcionario> buscarPorNome(String termo) {
        return repository.findByNomeContainingIgnoreCaseAndUsuarioIsNull(termo)
                .stream()
                .map(f -> new Funcionario(f.getId(), f.getNome()))
                .collect(Collectors.toList());
    }

}
