package br.com.bpadash.security;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenApp {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarTokenAdm(Authentication authentication) {
        Administrator administrador = (Administrator) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("Adm App dpadash")
                .setSubject(administrador.getCpf())
                .signWith(SignatureAlgorithm.HS512, "amdLogged")
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 100000)) // 10000
                .compact();
    }

    public String gerarToken(Authentication authentication) {
        User userLogged = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("User App dpadash")
                .setSubject(userLogged.getEmail())
                .signWith(SignatureAlgorithm.HS512, "userLogged")
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 100000)) // 10000
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey("userLogged").parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getId(String token) {
        Claims claims = Jwts.parser().setSigningKey("userLogged").parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey("userLogged").parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}