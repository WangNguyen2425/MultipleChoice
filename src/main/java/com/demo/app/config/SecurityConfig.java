package com.demo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder(15);
    }

    @Order(1)
    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/user/**").authorizeHttpRequests()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/user/**").hasAnyRole("ROLE_ADMIN", "ROLE_STUDENT")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login")
                    .loginProcessingUrl("/user/login/check")
                    .failureUrl("/user/login?error=true")
                    .and()
                    .logout().logoutUrl("/user/logout")
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .and()
                    .csrf().disable();

        }


    }
}
