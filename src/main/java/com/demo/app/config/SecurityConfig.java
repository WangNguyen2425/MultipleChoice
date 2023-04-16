package com.demo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/static/**", "/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login");
                })
                .build();
    }

}
