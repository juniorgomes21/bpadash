package br.com.bpadash.security;

import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenApp tokenApp;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository admRepository;

    //Conficaração para o JWT
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    //Configuração de autenticação
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuração de Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()

                .authorizeRequests()
                //---- auxiliar ----
                .antMatchers("/api/aux/**").permitAll()
                .antMatchers("/api/contact/message").permitAll()

                // ---- Padrão API ADM ----
                .antMatchers("/api/adm/auth").permitAll()
                .antMatchers("/api/adm/configurations/create").permitAll() //.hasAuthority(Role.ADMINISTRATOR.getName())
                .antMatchers("/api/adm/configurations/package").hasAuthority(Role.ADMINISTRATOR.getName()) //.hasAuthority(Role.ADMINISTRATOR.getName())
                .antMatchers("/api/adm/configurations/create/user").hasAuthority(Role.ADMINISTRATOR.getName())
                .antMatchers("/api/adm/sigtap/**").hasAuthority(Role.ADMINISTRATOR.getName())

                // ---- Padrão API USER -------------------------->
//                .antMatchers("/api/dash/bpa/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/logout/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/auth/login/employee").hasAuthority(Role.USER.getName())
                .antMatchers("/api/session/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/title/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/user/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/bpa/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/treatment/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/bpai/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/bpac/**").hasAuthority(Role.USER.getName())
                .antMatchers("/api/sigtap/**").hasAnyAuthority(Role.USER.getName(), Role.ADMINISTRATOR.getName())

                .antMatchers("/api/fpo/**").hasAnyAuthority(Role.USER.getName(), Role.ADMINISTRATOR.getName())
                .antMatchers("/api/prof/**").hasAnyAuthority(Role.USER.getName(), Role.ADMINISTRATOR.getName())

                // <--------------------------------------------->

                .antMatchers("/api/test/token").hasAnyAuthority(Role.USER.getName(), Role.ADMINISTRATOR.getName())

                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthenticacaoViaTokenFilter(tokenApp, userRepository, admRepository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configuração de recursos estáticos(js, css, img, etc.)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**");
    }
}

