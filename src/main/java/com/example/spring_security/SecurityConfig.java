package com.example.spring_security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("SHIELD Director")
                .build());

        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("hero")
                .password("password")
                .roles("SHIELD Hero")
                .build());

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                        .dispatcherTypeMatchers(HttpMethod.GET, DispatcherType.valueOf("/")).permitAll()
                        .dispatcherTypeMatchers(HttpMethod.valueOf("/avengers/assemble")).hasRole("SHIELD Hero")
                        .dispatcherTypeMatchers(HttpMethod.valueOf("/secret-bases")).hasRole("SHIELD Director")
                )
                .httpBasic(Customizer.withDefaults())
                .csrf().disable();

        return http.build();
    }

    @Bean
    public <WebSecurityConfigurerAdapter> WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(UserDetailsService userDetailsService) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService);
            }
        };
    }
}
