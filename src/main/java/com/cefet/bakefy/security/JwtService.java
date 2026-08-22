package com.cefet.bakefy.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cefet.bakefy.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey chave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        Date agora = new Date();
        Date expira = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(String.valueOf(usuario.getIdUsuario()))
                .claim("nome", usuario.getNmUsuario())
                .claim("email", usuario.getEmail())
                .claim("tipoUsuario", usuario.getTipoUsuario().name())
                .issuedAt(agora)
                .expiration(expira)
                .signWith(chave())
                .compact();
    }

    public Claims validarTokenEExtrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(chave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValido(String token) {
        try {
            validarTokenEExtrairClaims(token);
            return true;
} catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
