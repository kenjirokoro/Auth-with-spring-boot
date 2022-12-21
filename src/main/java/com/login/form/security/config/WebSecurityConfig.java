package com.login.form.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.login.form.appuser.AppUserService;
import com.login.form.security.PasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final AppUserService appUserService;
    private final PasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/api/registration/**")
                .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder.passwordEncoder());
        provider.setUserDetailsService(appUserService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) 
        throws Exception {

        return authConfig.getAuthenticationManager();
    }
    
}
