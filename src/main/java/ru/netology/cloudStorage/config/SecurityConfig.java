package ru.netology.cloudStorage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.netology.cloudStorage.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().and()
                .logout().disable()
                .authorizeRequests(
                        authz -> authz
                                .antMatchers("/login", "/users/create").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                )

                .logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl("/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}