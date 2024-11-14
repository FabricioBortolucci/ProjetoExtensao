package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.ItensVenda;
import br.com.produto.monkeyflip.model.VendaParcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItensVenda extends JpaRepository<ItensVenda, Long> {

    boolean existsByProduto_Id(Long id);
}
