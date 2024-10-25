package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.model.Macaco;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFuncionario extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findAllByOrderById(Pageable pageable);

    Funcionario findByNome(String nome);

    List<Funcionario> findByNomeContainingIgnoreCaseAndUsuarioIsNull(String termo);


    @Modifying
    @Transactional
    @Query(value = "UPDATE Funcionario SET usuario = NULL WHERE usuario.id = :idDoUsuario")
    void updateReferencia(@Param("idDoUsuario") Integer id);
}
