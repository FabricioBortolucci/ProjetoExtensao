package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Macaco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMacaco extends JpaRepository<Macaco, Long> {
    Page<Macaco> findAllByOrderById(Pageable pageable);


}
