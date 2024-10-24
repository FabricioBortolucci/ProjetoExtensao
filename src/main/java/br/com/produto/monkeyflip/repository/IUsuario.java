package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long> {

    @Query(value = "select * from usuario u where u.usu_nome = :nome and u.usu_senha = :senha", nativeQuery = true)
    Usuario buscarLogin(@Param("nome") String nome, @Param("senha") String senha);

    Page<Usuario> findAllByOrderById(Pageable pageable);

}
