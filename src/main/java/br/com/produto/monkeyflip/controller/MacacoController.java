package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Macaco;
import br.com.produto.monkeyflip.service.MacacoService;
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

@Controller
@CrossOrigin("*")
public class MacacoController {

    private final MacacoService macacoService;

    @Autowired
    public MacacoController(MacacoService macacoService) {
        this.macacoService = macacoService;
    }

    @GetMapping("/macacos")
    public String listFormMacaco(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Macaco> macacosPage = macacoService.paginacaoListarTodos(pageable);

        model.addAttribute("macacoList", macacosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", macacosPage.getTotalPages());
        return "cadForm/monkList";
    }

    @GetMapping("/macacos/novo")
    public String formMacaco(Model model) {
        model.addAttribute("macaco", new Macaco());
        return "cadForm/monkCad";
    }

    @PostMapping("/macacos/salvar")
    public String salvarMacaco(@Valid @ModelAttribute("macaco") Macaco macaco,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("macaco", macaco);
            return "cadForm/monkCad";
        }

        macacoService.salvar(macaco);
        return "redirect:/macacos";
    }


    @GetMapping("/macacos/excluir/{id}")
    public String excluirMacaco(@PathVariable Long id, Model model) {
        macacoService.excluir(id);
        return "redirect:/macacos";
    }

    @GetMapping("/macacos/editar/{id}")
    public String editarMacaco(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("macaco", macacoService.buscarPorId(id));
        return "cadForm/monkCad";
    }
}
