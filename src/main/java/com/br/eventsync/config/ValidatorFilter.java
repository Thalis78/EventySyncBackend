package com.br.eventsync.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.eventsync.entities.constantes.PapelUsuario;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidatorFilter implements Filter {

    private final static String URL_LOGIN =  "/auth/login";
    private final static String URL_REGISTRAR =  "/auth/registrar";
    private final static String SECRET = "a1f9c3d4e5b67890c2f3a4b5d6e7f8091a2b3c4d5e6f7081920a1b2c3d4e5f60";

    private final JWTVerifier verifier;

    public ValidatorFilter() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        this.verifier = JWT.require(algorithm).build();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String URL = httpServletRequest.getRequestURI();

        if ((URL.equals(URL_LOGIN) || URL.equals(URL_REGISTRAR)) && httpServletRequest.getMethod().equals("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token de autorização ausente ou mal formatado!");
            return;
        }

        String jwtToken = token.substring(7);

        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (JWTVerificationException exception) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado: " + exception.getMessage());
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erro ao processar o token: " + e.getMessage());
        }
    }
}
