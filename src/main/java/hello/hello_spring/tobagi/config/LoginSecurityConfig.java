package hello.hello_spring.tobagi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig {
    @Bean
    //SecurityFilterChain: 보안 필터 체인을 정의 -> HTTP 요청에 대한 보안을 구성
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //authorizeHttpRequests: 요청 권한 설정 정의
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login**", "/webjars/**").permitAll()
                                .anyRequest().authenticated()
                )
                //oauth2Login: OAuth2 로그인 설정 정의
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .defaultSuccessUrl("/success", true)
                )
                //logout: 로그아웃 설정을 정의합니다.
                .logout(logout ->
                        logout
//                                .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL
                                .logoutSuccessUrl("/index") // 로그아웃 성공 후 리다이렉트할 URL
                                .invalidateHttpSession(true) // 세션 무효화
                                .deleteCookies("JSESSIONID") // 지정된 쿠키 삭제
                                .clearAuthentication(true) // 인증 정보 제거
                                .permitAll()
                )
                //exceptionHandling: 예외 처리 설정 정의
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                );

        return http.build();
    }

    @Bean
    //WebSecurityCustomizer: 특정 URL 패턴에 대한 보안 설정을 무시하도록 구성합니다.
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/public/**");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public String String() {
        return new String();
    }
}
