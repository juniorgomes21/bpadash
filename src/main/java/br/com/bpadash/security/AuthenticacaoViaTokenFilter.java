package br.com.bpadash.security;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.email.EmailActivationsRepository;
import br.com.bpadash.repository.user.UserRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthenticacaoViaTokenFilter extends OncePerRequestFilter {

    private final TokenApp tokenApp;

    private final UserRepository userRepository;

    private final AdministratorRepository admRepository;
    
    public AuthenticacaoViaTokenFilter(TokenApp tokenApp, UserRepository userRepository, AdministratorRepository admRepository) {
        this.tokenApp = tokenApp;
        this.admRepository = admRepository;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        if(token != null) {
            boolean valid = tokenApp.isTokenValid(token);

            if(valid) {
                this.authenticate(token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        String subject = tokenApp.getSubject(token);

        UsernamePasswordAuthenticationToken authentication;

        Optional<User> userOptional = userRepository.findByKeyEmail(EnCryptionAESService.decrypt(subject));

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return;
        }

        Optional<Administrator> administratorOptional = admRepository.findByKeyEmail(EnCryptionAESService.decrypt(subject));

        if (administratorOptional.isPresent()){
            Administrator adm = administratorOptional.get();

            authentication = new UsernamePasswordAuthenticationToken(adm, null, adm.getAuthorities());

        } else {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(Role.TOKEN_TEMP.getName()));

            authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}