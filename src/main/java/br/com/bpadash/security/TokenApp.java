package br.com.bpadash.security;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenApp {

    private static final Dotenv dotenv = Dotenv.load();
    private final String expiration = dotenv.get("JWT_EXPIRATION");
    private final String issuer = dotenv.get("JWT_ISSUER");
    private static final String RSA_PRIVATE_KEY = dotenv.get("RSA_PRIVATE_KEY");
    private static final String RSA_PUBLIC_KEY = dotenv.get("RSA_PUBLIC_KEY");


    public String gerarTokenAdm(Authentication authentication) {
        Administrator administrator = (Administrator) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(EnCryptionAESService.encrypt(administrator.getKeyEmail()))
                .signWith(SignatureAlgorithm.RS256, getPrivateKey())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .compact();
    }

    public String gerarToken(User user) {

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(EnCryptionAESService.encrypt(user.getKeyEmail()))
                .signWith(SignatureAlgorithm.RS256, getPrivateKey())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration))) // 1h 23m // new Date(System.currentTimeMillis() + 3600 * 2000) 1h
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    private PrivateKey getPrivateKey() {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(RSA_PRIVATE_KEY);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static PublicKey getPublicKey() {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(RSA_PUBLIC_KEY);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter chave pública.", e);
        }
    }
}