package com.cefet.bakefy.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final List<String> ROTAS_PUBLICAS_POST = List.of(
            "/api/v1/usuarios/login",
            "/api/v1/clientes",
            "/api/v1/empresas"
    );

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String metodo = request.getMethod();
        String caminho = request.getRequestURI();

        boolean requisicaoPublica =
                "GET".equalsIgnoreCase(metodo)
                || "OPTIONS".equalsIgnoreCase(metodo)
                || ("POST".equalsIgnoreCase(metodo)
                && ROTAS_PUBLICAS_POST.contains(caminho));

        if (requisicaoPublica) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || header.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token não informado."
            );
            return;
        }

        String token = header.startsWith("Bearer ")
                ? header.substring(7)
                : header;

        try {

            Claims claims = jwtService.validarTokenEExtrairClaims(token);

            String idUsuario = claims.getSubject();
            String tipoUsuario = claims.get("tipoUsuario", String.class);

            request.setAttribute("idUsuario", idUsuario);
            request.setAttribute("tipoUsuario", tipoUsuario);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            idUsuario,
                            null,
                            AuthorityUtils.NO_AUTHORITIES
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inválido ou expirado."
            );

            return;
        }

        filterChain.doFilter(request, response);
    }
}