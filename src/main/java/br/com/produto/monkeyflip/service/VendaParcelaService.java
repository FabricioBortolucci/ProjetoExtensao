package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.model.Venda;
import br.com.produto.monkeyflip.repository.IVenda;
import br.com.produto.monkeyflip.repository.IVendaParcela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendaParcelaService {

    @Autowired
    private IVendaParcela repository;


}
