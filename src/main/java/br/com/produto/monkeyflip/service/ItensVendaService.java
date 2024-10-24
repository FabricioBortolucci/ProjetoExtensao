package br.com.produto.monkeyflip.service;

import br.com.produto.monkeyflip.repository.IItensVenda;
import br.com.produto.monkeyflip.repository.IVendaParcela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItensVendaService {

    @Autowired
    private IItensVenda repository;

}
