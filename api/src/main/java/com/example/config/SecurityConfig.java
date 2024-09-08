//package com.example.config;
//
//import jakarta.servlet.DispatcherType;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
//        requestCache.setMatchingRequestParameterName(null);
//        http
//                .requestCache(cache -> cache.requestCache(requestCache))
//                .authorizeHttpRequests(requests -> requests
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers("/boundary", "/boundary/**").permitAll()
//                        .requestMatchers("/login/**", "/oauth2/**").permitAll()
//                )
//                .anonymous(anonymous -> anonymous
//                        .authorities("ROLE_ANONYMOUS")
//                )
//                .csrf(csrf -> csrf.disable());
////                .oauth2Login(oauth2 -> oauth2
////                        .loginPage("/login")
////                        .permitAll()
////                        .defaultSuccessUrl("/home", true).permitAll()
////                        .failureUrl("/login?error=true").permitAll()
////                )
////                .exceptionHandling(handling -> handling
////                        .authenticationEntryPoint((request, response, authException) ->
////                                response.sendRedirect("/login"))
////                );
//        return http.build();
//    }
//}