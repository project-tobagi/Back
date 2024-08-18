package hello.tobagi.tobagi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 접근 권한 설정(todo)
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll()
//                                .requestMatchers("/map/**", "/boundary/**","/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/", "/auth/google/callback", "/auth/**", "/login", "/error","/boundary/**").permitAll()
//                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                );
        return http.build();
    }
}