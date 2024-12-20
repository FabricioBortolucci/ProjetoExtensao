package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.*;
import br.com.produto.monkeyflip.model.enums.PlanoPagamento;
import br.com.produto.monkeyflip.model.enums.TipoPagamento;
import br.com.produto.monkeyflip.service.ItensVendaService;
import br.com.produto.monkeyflip.service.MacacoService;
import br.com.produto.monkeyflip.service.VendaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("*")
public class VendaController {

    private final VendaService vendaService;
    private final ItensVendaService itensVendaService;
    private final MacacoService macacoService;

    @Autowired
    public VendaController(VendaService vendaService, ItensVendaService itensVendaService, MacacoService macacoService) {
        this.vendaService = vendaService;
        this.itensVendaService = itensVendaService;
        this.macacoService = macacoService;
    }

    @GetMapping("/vendas")
    public String listaVendas(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Venda> vendaPage = vendaService.paginacaoListarTodos(pageable);

        model.addAttribute("vendaList", vendaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vendaPage.getTotalPages());
        model.addAttribute("size", vendaPage.getSize());
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
                               Model model,
                               HttpSession session,
                               HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("venda", venda);
            return "vendForm/vendCad";
        }

        String userId = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("usuarioId")) {
                userId = cookie.getValue();
                break;
            }
        }
        if (venda.getData() == null || venda.getData().isBefore(LocalDate.now())) {
            model.addAttribute("macacoDataErro", "A data está nula ou é menor que a data atual");
            model.addAttribute("tipoPagamentos", TipoPagamento.values());
            model.addAttribute("planoPagamentos", PlanoPagamento.values());
            return "vendForm/vendCad";
        }
        if (venda.getMacacoId() == null) {
            model.addAttribute("macacoIdErro", "Id do macaco está nulo");
            model.addAttribute("tipoPagamentos", TipoPagamento.values());
            model.addAttribute("planoPagamentos", PlanoPagamento.values());
            return "vendForm/vendCad";
        }
        if (venda.getPlanoPagamento() == null) {
            model.addAttribute("planoPagamentosErro", "plano de pagamento está nulo");
            model.addAttribute("tipoPagamentos", TipoPagamento.values());
            model.addAttribute("planoPagamentos", PlanoPagamento.values());
            return "vendForm/vendCad";
        }
        if (venda.getTipoPagamento() == null) {
            model.addAttribute("tipoPagamentosErro", "tipo de pagamento está nulo");
            model.addAttribute("tipoPagamentos", TipoPagamento.values());
            model.addAttribute("planoPagamentos", PlanoPagamento.values());
            return "vendForm/vendCad";
        }
        List<VendaParcela> parcelas = (List<VendaParcela>) session.getAttribute("parcelasCalculadas");
        if (!venda.getPlanoPagamento().equals(PlanoPagamento.A)) {
            if (parcelas == null) {
                model.addAttribute("parcelaErro", "Gere as parcelas");
                model.addAttribute("tipoPagamentos", TipoPagamento.values());
                model.addAttribute("planoPagamentos", PlanoPagamento.values());
                return "vendForm/vendCad";
            }
        }

        vendaService.salvar(venda, parcelas, userId);
        session.removeAttribute("parcelasCalculadas");
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
    public List<Map<String, String>> filtrarTipoPagamento(@RequestBody PlanoPagamento planoPagamento) {
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

    @GetMapping("/vendas/buscarProd")
    @ResponseBody
    public List<Map<String, Object>> buscarProduto(@RequestParam("term") String termo) {
        List<Macaco> macacos = macacoService.buscarPorNome(termo);

        // Retorna uma lista de mapas com 'label' e 'value'
        return macacos.stream()
                .map(monk -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", monk.getNome());
                    map.put("id", monk.getId());
                    map.put("preco", monk.getPrecoUnitario());
                    map.put("estoque", monk.getQuantidade());
                    return map;
                })
                .collect(Collectors.toList());
    }


    @PostMapping("/vendas/calcularParcelas")
    public ResponseEntity<List<VendaParcela>> calcularParcelas(
            @RequestParam("precoMacaco") String precoMacaco,
            @RequestParam("quantParcelas") int quantParcelas,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
            @ModelAttribute("venda") Venda venda,
            HttpSession session) {

        venda.setParcelas(new ArrayList<>());
        if (venda.getMacacoId() == null) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        if (dataVencimento.isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        if (quantParcelas > 12) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        List<VendaParcela> parcelas = new ArrayList<>();
        BigDecimal valorMacaco = new BigDecimal(precoMacaco);
        BigDecimal valorParcela = valorMacaco.divide(new BigDecimal(quantParcelas), 2, RoundingMode.HALF_UP);

        for (int numparcela = 1; numparcela <= quantParcelas; numparcela++) {
            VendaParcela parcela = new VendaParcela(valorParcela, false, dataVencimento, venda, numparcela);
            parcelas.add(parcela);
            dataVencimento = dataVencimento.plusDays(30);
        }

        session.setAttribute("parcelasCalculadas", parcelas);
        session.setAttribute("precoMacaco", precoMacaco);
        return ResponseEntity.ok(parcelas);
    }


    @GetMapping("/vendas/visualizar/{id}")
    public String visualizarVenda(@PathVariable Long id, Model model) {
        Venda venda = vendaService.buscarPorId(id);
        venda.setParcelas(venda.getParcelas().stream()
                .sorted(Comparator.comparingInt(VendaParcela::getNumparcela))
                .collect(Collectors.toList()));
        model.addAttribute("venda", venda);
        return "vendForm/venView";
    }

    @PostMapping("/vendas/buscarvendas")
    public String buscarVendas(@RequestParam("idpesquisa") String idpesquisa, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Venda> vendaPage = vendaService.buscarVendasPorId(pageable, idpesquisa);


        model.addAttribute("vendaList", vendaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vendaPage.getTotalPages());
        model.addAttribute("size", vendaPage.getSize());
        if (idpesquisa.isEmpty()) {
            return "redirect:/vendas";
        }

        return "vendForm/venList";
    }


    // ------------------------------- //


}
