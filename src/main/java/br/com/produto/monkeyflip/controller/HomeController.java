package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin("*")
public class HomeController extends AbstractController {

    @Autowired
    private LoginController loginController;

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "redirect:/login"; // Redireciona para o login se o usuário não estiver na sessão
        }
        return "fragments/home";
    }
}
