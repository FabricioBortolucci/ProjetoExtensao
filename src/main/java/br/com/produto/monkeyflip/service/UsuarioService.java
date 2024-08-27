package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario repository;


    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario findByUsu(Usuario usuario){
        return repository.buscarLogin(usuario.getUsuNome(), usuario.getUsuSenha());
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }
}
