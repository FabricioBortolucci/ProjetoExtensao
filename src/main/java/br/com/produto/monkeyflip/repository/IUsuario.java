package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long> {
}
