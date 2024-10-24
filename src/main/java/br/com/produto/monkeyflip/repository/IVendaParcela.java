package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.VendaParcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendaParcela extends JpaRepository<VendaParcela, Long> {

}
