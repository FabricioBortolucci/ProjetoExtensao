package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.CookieService;
import br.com.produto.monkeyflip.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String erro, Model model) {
        if (erro != null) {
            model.addAttribute("erro", erro);
        }
        return "loginForm/login";
    }

    @PostMapping("/logar")
    public String logar(Model model, Usuario usuarioParam, HttpServletResponse response, HttpServletRequest request) {
        Usuario usu = this.usuarioService.findByUsu(usuarioParam);
        if (usu != null) {
            int maxAge = (60 * 60); // 1 hora de cookie
            CookieService.setCookie(response, "usuarioId", usu.getId().toString(), maxAge);
            request.getSession().setAttribute("user", usu);
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
