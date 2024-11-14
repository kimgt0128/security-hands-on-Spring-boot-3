package com.wondrous.test_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티가 관리하도록 설정
public class SecurityConfig {

    @Bean //스프링 빈이 관리하여 다른 곳에서 사용 가능
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //사이트 위변조 방지 설정 해제(개발 환경에서만)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // 로그인 없이 접근 가능하도록 설정
                        .requestMatchers("/main", "/user/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                )
                .sessionManagement((auth) -> auth
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(true)

                )
                //세션 고정 보호 설정
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );
        return http.build();
    }
}
