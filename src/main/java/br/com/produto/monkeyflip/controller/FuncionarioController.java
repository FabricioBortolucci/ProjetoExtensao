package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.model.Macaco;
import br.com.produto.monkeyflip.service.FuncionarioService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin("*")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionarios")
    public String funcionariosList(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Funcionario> funcionarioPage = funcionarioService.paginacaoListarTodos(pageable);

        model.addAttribute("funcList", funcionarioPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", funcionarioPage.getTotalPages());

        return "funcForm/funcList";
    }

    @GetMapping("/funcionarios/novo")
    public String newFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcForm/funcCad";
    }

    @PostMapping("/funcionarios/salvar")
    public String salvarFuncionario(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("funcionario", funcionario);
            return "funcForm/funcCad";
        }

        funcionarioService.salvar(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/funcionarios/excluir/{id}")
    public String excluirFuncionario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        if (funcionarioService.funcionarioRelacionadoUsuario(id)) {
            redirectAttributes.addFlashAttribute("erroExcluir", true);
            return "redirect:/funcionarios";
        }

        funcionarioService.excluir(id);
        return "redirect:/funcionarios";
    }

    @GetMapping("/funcionarios/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("funcionario", funcionarioService.buscarPorId(id));
        return "funcForm/funcCad";
    }

}
