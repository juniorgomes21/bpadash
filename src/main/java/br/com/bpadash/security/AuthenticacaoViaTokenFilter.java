package br.com.bpadash.security;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.User;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenApp tokenApp;

    private UserRepository userRepository;

    private AdministratorRepository admRepository;
    
    public AuthenticacaoViaTokenFilter(TokenApp tokenApp, UserRepository userRepository, AdministratorRepository admRepository) {
        this.tokenApp = tokenApp;
        this.admRepository = admRepository;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        if(token != null) {
            boolean valido = tokenApp.isTokenValid(token);

            if(valido) {
                String usarioEmail = tokenApp.getEmailx(token);
                Optional<User> userOptional = userRepository.findByEmail(usarioEmail);

                if (userOptional.isPresent()) {
                    authenticateUser(token);
                } else {
                    authenticateADM(token);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // Para o user
    private void authenticateUser(String token) {
        String emailJogador = tokenApp.getEmailx(token);
        User user = this.userRepository.findByEmail(emailJogador).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Para ADM
    private void authenticateADM(String token) {
        String idAdm = tokenApp.getEmailx(token);
        Administrator adm = this.admRepository.findByCpf(idAdm).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(adm, null, adm.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}