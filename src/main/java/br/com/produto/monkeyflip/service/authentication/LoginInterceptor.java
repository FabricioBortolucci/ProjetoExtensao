package br.com.produto.monkeyflip.service.authentication;

import br.com.produto.monkeyflip.model.Usuario;
import br.com.produto.monkeyflip.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        if (CookieService.getCookie(request, "usuarioId") != null) {
            if (user != null) {
                return true;
            }
        }

        response.sendRedirect("/login");
        return false;
    }

}
