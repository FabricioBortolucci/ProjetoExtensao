package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
public class TesteController {

    private UsuarioService usuarioService;

    public TesteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/index")
    public String showForm() {
        return "index"; // Nome do template Thymeleaf para o formulário de cadastro
    }

    @GetMapping("/listar")
    public String listaUsers(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "form/listar"; // Nome do template Thymeleaf para a listagem de usuários
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario, Model model) {
        usuarioService.salvar(usuario);
        model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
        return "redirect:/listar"; // Redireciona para a URL /listar após o cadastro
    }

}
