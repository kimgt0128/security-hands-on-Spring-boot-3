package com.wondrous.test_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티가 관리하도록 설정
public class SecurityConfig {

    @Bean //스프링 빈이 관리하여 다른 곳에서 사용 가능
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        /*****6.3.x 버전 이전 RoleHierarchy 설정**********************
         ----------------------------------------------------------
         RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
         roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n" +
                 "ROLE_MANAGER > ROLE_USER");
         return roleHierarchy;
         ----------------------------------------------------------
         6.3.x 이후 버전(ROLE_접두사 수동)
         ----------------------------------------------------------
         return RoleHierarchyImpl.fromHierarchy("""
                 ROLE_ADMIN > ROLE_MANAGER
                 ROLE_MANAGER > ROME_USER
                 """);
         -----------------------------------------------------------
         */
        // ROLE 접두사 자동생성
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MANAGER")
                .role("MANAGER").implies("USER")
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /**
                 * 사이트 위변조 방지 설정 해제
                 * 개발 환경 or JWT 구현 방식에서만 해제
                 * 아무 설정도 없으면 enable이 기본 설정
                 * .csrf(AbstractHttpConfigurer::disable)
                 */
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // 로그인 없이 접근 가능하도록 설정
                        .requestMatchers("/main", "/user/**").hasAnyRole("USER")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER")
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
                )
                //로그아웃 엔드포인트 설정
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }

//    @Bean
//    public UserDetailsService users() {
//        BCryptPasswordEncoder passwordEncoder = bCryptPasswordEncoder();
//        User.UserBuilder users = User.builder();
//
//        UserDetails adminUser = users
//                .username("admin")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("ADMIN").build();
//
//        UserDetails managerUser = users
//                .username("manager")
//                .password(passwordEncoder.encode("1234"))
//                .roles("MANAGER").build();
//
//        UserDetails userUser = users
//                .username("user")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(adminUser, managerUser, userUser);
//
//    }
}
