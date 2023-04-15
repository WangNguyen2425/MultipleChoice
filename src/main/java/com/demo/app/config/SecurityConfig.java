package com.demo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig{

        @Bean
        public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception{
            http.csrf().disable().authorizeHttpRequests().requestMatchers("/admin/**").authenticated().and().formLogin().loginPage("/admin/login");

            return http.build();
        }
    }

}
