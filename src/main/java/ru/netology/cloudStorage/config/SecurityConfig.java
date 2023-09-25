package ru.netology.cloudStorage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.netology.cloudStorage.security.JwtFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity()
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .csrf(AbstractHttpConfigurer::disable)
                .cors((AbstractHttpConfigurer::disable))

                .authorizeHttpRequests((request) ->
                        request.requestMatchers("/login*")
                                .permitAll()
                                .anyRequest()
                                .authenticated())

                .formLogin(request -> request.loginPage("/login"))

                .logout(request -> request.logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login"))

                .sessionManagement(request -> request.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}