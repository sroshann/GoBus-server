//package com.group3.goBus.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                // Disable CSRF for testing APIs (especially for Postman or frontend dev)
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // New Lambda-style authorization API
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/**").permitAll() // allow signup/login endpoints
//                        .anyRequest().authenticated() // everything else needs authentication
//                )
//
//                // Disable form-based login (use API instead)
//                .formLogin(AbstractHttpConfigurer::disable)
//
//                // Disable HTTP Basic (optional, only if you’re using token-based auth)
//                .httpBasic(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }
//
//}


package com.group3.goBus.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (enable later for production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll() // allow signup/login APIs
                        .anyRequest().permitAll() // allow everything for now (you can restrict later)
                )
                .formLogin(form -> form.disable()) // disable default login form
                .httpBasic(basic -> basic.disable()); // disable HTTP basic auth

        return http.build();
    }
}
