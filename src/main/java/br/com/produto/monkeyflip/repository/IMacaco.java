package br.com.produto.monkeyflip.repository;

import br.com.produto.monkeyflip.model.Macaco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IMacaco extends JpaRepository<Macaco, Long> {
    Page<Macaco> findAllByOrderById(Pageable pageable);


    List<Macaco> findByNomeContainingIgnoreCase(String termo);

    Macaco getMacacoById(Long id);
}
