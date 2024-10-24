package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.exceptions.RecursoNaoEncontradoException;
import br.com.produto.monkeyflip.model.Funcionario;
import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.UsuarioService;
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

import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public String usuariosList(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarioPage = usuarioService.paginacaoListarTodos(pageable);

        model.addAttribute("usuList", usuarioPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuarioPage.getTotalPages());

        return "usuForm/usuList";
    }

    @GetMapping("/usuarios/novo")
    public String newUsuario(Model model) {
        model.addAttribute("newUser", new Usuario());
        return "usuForm/usuCad";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("newUser") Usuario usuario,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newUser", usuario);
            return "usuForm/usuCad";
        }

        try {
            usuarioService.salvarUsuarioComFuncionario(usuario);
        } catch (RecursoNaoEncontradoException e) {
            model.addAttribute("erro", e.getMessage());
            return "usuForm/usuCad";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, Model model) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("newUser", usuarioService.buscarPorId(id));
        return "usuForm/usuCad";
    }

    @GetMapping("/usuarios/buscarFunc")
    @ResponseBody
    public List<Map<String, Object>> buscarFuncionario(@RequestParam("term") String termo) {
        List<Funcionario> funcionarios = usuarioService.buscarPorNome(termo);

        // Retorna uma lista de mapas com 'label' e 'value'
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getUsuario() == null) // Filtra os funcionários sem usuário
                .map(funcionario -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", funcionario.getNome()); // Nome do funcionário
                    map.put("value", funcionario.getId()); // ID do funcionário
                    return map;
                })
                .collect(Collectors.toList());
    }

}