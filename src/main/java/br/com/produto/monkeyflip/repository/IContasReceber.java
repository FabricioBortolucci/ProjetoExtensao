package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.ContaReceber;
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
public interface IContasReceber extends JpaRepository<ContaReceber, Long> {
    Page<ContaReceber> findAllByOrderById(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE ContaReceber cr SET cr.pago = true WHERE cr.id = :id")
    void atualizarStatusPago(@Param("id") Long id);
}
