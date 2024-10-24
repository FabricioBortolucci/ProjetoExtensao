package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("usuario")
    public Usuario addUsuarioToModel(HttpServletRequest request) {
        return (Usuario) request.getSession().getAttribute("user");
    }
}
