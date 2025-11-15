package group.triade.catalogo.config;

import com.auth0.jwt.algorithms.Algorithm;
import group.triade.catalogo.entities.Lojista;
import group.triade.catalogo.repositories.LojistaRepository;
import group.triade.catalogo.services.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private LojistaRepository adminRepository;

    private Algorithm jwtAlg = Algorithm.HMAC256("my-secret");

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();


        if (uri.equals("/auth") ||
                (uri.equals("/usuarios") && request.getMethod().equals("POST")) ||
                uri.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = extraiTokenHeader(request);

        if (token != null) {
            String login = autenticacaoService.validaToken(token);
            Lojista lojista = adminRepository.findByEmail(login);

            if (lojista != null) {
                var autentication = new UsernamePasswordAuthenticationToken(
                        lojista, null, lojista.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    public String extraiTokenHeader(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7); // remove "Bearer "
    }
}
