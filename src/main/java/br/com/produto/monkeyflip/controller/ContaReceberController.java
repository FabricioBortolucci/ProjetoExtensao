package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.ContaReceber;
import br.com.produto.monkeyflip.model.Venda;
import br.com.produto.monkeyflip.model.VendaParcela;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Page<ContaReceber> contaRecebers = contaReceberService.paginacaoListarTodos(pageable);

        model.addAttribute("crList", contaRecebers.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contaRecebers.getTotalPages());
        model.addAttribute("size", contaRecebers.getSize());
        return "contasReceberForm/receberList";
    }

    @GetMapping("/contasReceber/alterarPago/{id}")
    public String alterarPago(@PathVariable Long id) {
        contaReceberService.updateParaPago(id);
        return "redirect:/contasReceber";
    }

    @GetMapping("/contasReceber/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        ContaReceber contaReceber = contaReceberService.buscarPorId(id);
        Venda venda = contaReceber.getVenda();
        venda.setParcelas(venda.getParcelas().stream()
                .sorted(Comparator.comparingInt(VendaParcela::getNumparcela))
                .collect(Collectors.toList()));

        model.addAttribute("contaReceber", contaReceber);
        model.addAttribute("venda", venda);
        return "contasReceberForm/receberView";
    }

    @PostMapping("/contasReceber/buscarcontas")
    public String buscarVendas(@RequestParam("idpesquisa") String idpesquisa, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ContaReceber> contaRecebers = contaReceberService.buscarContaPorVendaId(pageable, idpesquisa);


        model.addAttribute("crList", contaRecebers.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contaRecebers.getTotalPages());
        model.addAttribute("size", contaRecebers.getSize());
        if (idpesquisa.isEmpty()) {
            return "redirect:/contasReceber";
        }

        return "contasReceberForm/receberList";
    }

}
