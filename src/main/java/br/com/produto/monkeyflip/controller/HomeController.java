package br.com.produto.monkeyflip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin("*")
public class HomeController extends AbstractController {

    @GetMapping("/home")
    public String home(Model model) {
        return "fragments/home";
    }

    @GetMapping("/suporte")
    public String suporte(Model model) {
        return "suporte/suporteView";
    }


}
