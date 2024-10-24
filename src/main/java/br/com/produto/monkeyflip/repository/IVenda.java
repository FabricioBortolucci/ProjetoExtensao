package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenda extends JpaRepository<Venda, Long> {


    Page<Venda> findAllByOrderById(Pageable pageable);

}
