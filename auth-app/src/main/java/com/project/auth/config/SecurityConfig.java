package com.project.auth.config;


import com.project.auth.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.project.auth.util.SecurityConfigConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFiler;

    private final AuthenticationProvider authenticationProvider;

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {
        security
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(ADMIN_MATCHER)
                .hasRole(ADMIN_ROLE)
                .requestMatchers(USER_MATCHER)
                .hasRole(USER_ROLE)
                .requestMatchers(OTHER_MATCHER)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFiler, UsernamePasswordAuthenticationFilter.class);
        return security.build();

    }
}
