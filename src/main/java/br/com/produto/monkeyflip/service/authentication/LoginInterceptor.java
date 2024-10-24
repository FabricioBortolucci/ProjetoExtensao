package br.com.produto.monkeyflip.service.authentication;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.CookieService;
import br.com.produto.monkeyflip.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final UsuarioService usuarioService;

    @Autowired
    public LoginInterceptor(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String usuarioId = CookieService.getCookie(request, "usuarioId");
        if (usuarioId != null) {
            Optional<Usuario> usu = Optional.ofNullable(usuarioService.buscarPorId(Long.parseLong(usuarioId)));

            if (usu.isPresent()) {
                request.getSession().setAttribute("user", usu.get());
                return true;
            }
        }

        response.sendRedirect("/login");
        return false;
    }

}
