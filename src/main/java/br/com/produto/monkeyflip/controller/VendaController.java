package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Venda;
import br.com.produto.monkeyflip.model.enums.PlanoPagamento;
import br.com.produto.monkeyflip.model.enums.TipoPagamento;
import br.com.produto.monkeyflip.service.VendaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@CrossOrigin("*")
public class VendaController {

    private final VendaService vendaService;

    @Autowired
    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping("/vendas")
    public String listaVendas(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Venda> macacosPage = vendaService.paginacaoListarTodos(pageable);

        model.addAttribute("vendaList", macacosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", macacosPage.getTotalPages());
        return "vendForm/venList";
    }

    @GetMapping("/vendas/novo")
    public String formVendas(Model model) {
        Venda venda = new Venda();
        venda.setData(LocalDate.now());

        model.addAttribute("venda", venda);

        model.addAttribute("tipoPagamentos", TipoPagamento.values());
        model.addAttribute("planoPagamentos", PlanoPagamento.values());

        return "vendForm/vendCad";
    }

    @PostMapping("/vendas/salvar")
    public String salvarVendas(@Valid @ModelAttribute("venda") Venda venda,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("venda", venda);
            return "vendForm/vendCad";
        }

        vendaService.salvar(venda);
        return "redirect:/vendas";
    }

    @GetMapping("/vendas/excluir/{id}")
    public String excluirVenda(@PathVariable Long id, Model model) {
        vendaService.excluir(id);
        return "redirect:/vendas";
    }

    @GetMapping("/vendas/editar/{id}")
    public String editarVenda(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("venda", vendaService.buscarPorId(id));
        return "vendForm/vendCad";
    }

    @PostMapping("vendas/filtrarTipos")
    @ResponseBody
    public List<Map<String, String>> filtrarTipoPagamento(@RequestBody PlanoPagamento planoPagamento){
        PlanoPagamento plano = PlanoPagamento.valueOf(String.valueOf(planoPagamento));
        List<TipoPagamento> list = TipoPagamento.buscarTiposPorPlano(plano);

        // Convertendo para uma lista de mapas para enviar ao JavaScript
        List<Map<String, String>> result = new ArrayList<>();
        for (TipoPagamento tipo : list) {
            Map<String, String> tipoMap = new HashMap<>();
            tipoMap.put("value", tipo.name()); // Nome do enum como valor
            tipoMap.put("descricao", tipo.descricao); // Descrição do tipo
            result.add(tipoMap);
        }
        return result;
    }
}
