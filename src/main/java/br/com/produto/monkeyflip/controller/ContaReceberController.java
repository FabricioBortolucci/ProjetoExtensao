package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.ContaReceber;
import br.com.produto.monkeyflip.service.ContaReceberService;
import br.com.produto.monkeyflip.service.MacacoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class ContaReceberController {

    private final ContaReceberService contaReceberService;

    @Autowired
    public ContaReceberController(ContaReceberService contaReceberService) {
        this.contaReceberService = contaReceberService;
    }

    @GetMapping("/contasReceber")
    public String listFormCR(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ContaReceber> macacosPage = contaReceberService.paginacaoListarTodos(pageable);

        model.addAttribute("crList", macacosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", macacosPage.getTotalPages());
        return "contasReceberForm/receberList";
    }

    @GetMapping("/contasReceber/alterarPago/{id}")
    public String alterarPago(@PathVariable Long id) {
        contaReceberService.updateParaPago(id);
        return "redirect:/contasReceber";
    }

}
