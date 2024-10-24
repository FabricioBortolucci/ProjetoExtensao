package br.com.produto.monkeyflip.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public String handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request, Model model) {
        model.addAttribute("erro", ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
