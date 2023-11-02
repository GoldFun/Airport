package com.example.aiport.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;


@TestConfiguration
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class SecurityConfigurationTest {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.cors();


        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/auth/register")
                .permitAll()
                .antMatchers("/auth/help")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        return http.build();
    }

    @Bean
    public SecurityContext securityContext(){
        return new SecurityContextImpl();
    }
}