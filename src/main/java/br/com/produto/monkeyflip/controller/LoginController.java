package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.CookieService;
import br.com.produto.monkeyflip.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class LoginController {

    private final UsuarioService usuarioService;

    @Autowired
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String erro, Model model) {
        model.addAttribute("loginUser", new Usuario());
        if (erro != null) {
            model.addAttribute("erro", erro);
        }
        return "loginForm/login";
    }

    @PostMapping("/logar")
    public String logar(@ModelAttribute("loginUser") Usuario usuarioParam, Model model,  HttpServletResponse response, HttpServletRequest request) {
        if (usuarioParam == null || usuarioParam.getUsuNome() == null) {
            return "redirect:/login?erro=Preencha os campos corretamente";
        }
        Usuario usu = this.usuarioService.findByUsu(usuarioParam);
        if (usu != null) {
            int maxAge = (60 * 60); // 1 hora de cookie
            CookieService.setCookie(response, "usuarioId", usu.getId().toString(), maxAge);
            return "redirect:home";
        }
        model.addAttribute("erro", "");
        return "redirect:/login?erro=Usuario ou Senha invalidos";
    }

    @GetMapping("/sair")
    public String sair(HttpServletResponse response) {
        CookieService.setCookie(response, "usuarioId", "", 0);
        return "redirect:/login";

    }

}
