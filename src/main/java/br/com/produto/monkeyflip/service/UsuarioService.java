package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.exceptions.RecursoNaoEncontradoException;
import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final IUsuario repository;

    private final FuncionarioService funcionarioService;

    @Autowired
    public UsuarioService(IUsuario repository, FuncionarioService funcionarioService) {
        this.repository = repository;
        this.funcionarioService = funcionarioService;
    }

    public Page<Usuario> paginacaoListarTodos(Pageable pageable) {
        return repository.findAllByOrderById(pageable);
    }


    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario findByUsu(Usuario usuario) {
        return repository.buscarLogin(usuario.getUsuNome(), usuario.getUsuSenha());
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public void excluir(Long id) {
        funcionarioService.excluirReferenciaUsuario(Integer.parseInt(String.valueOf(id)));
        repository.deleteById(id);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void salvarUsuarioComFuncionario(Usuario usuario) {
        if (usuario.getFuncionario() == null || usuario.getFuncionario().getId() == null)
            throw new RecursoNaoEncontradoException("O campo de funcionário está vazio");

        Funcionario funcionario = funcionarioService.buscarPorId(usuario.getFuncionario().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));

        if (funcionario.getUsuario() != null)
            if (!funcionario.getUsuario().equals(usuario))
                throw new RecursoNaoEncontradoException("Funcionário já vinculado a um usuário. Cadastre um novo funcionário.");

        funcionario.setUsuario(usuario);
        usuario.setFuncionario(funcionario);
        repository.save(usuario);
    }

    public List<Funcionario> buscarPorNome(String termo) {
        return funcionarioService.buscarPorNome(termo);
    }
}
