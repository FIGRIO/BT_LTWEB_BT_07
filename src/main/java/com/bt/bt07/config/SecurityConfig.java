package com.bt.bt07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.bt.bt07.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            return userRepo.findById(username).map(u -> {
                String[] roles = u.isAdmin() ? new String[] { "ADMIN" } : new String[] { "USER" };
                UserDetails ud = User.withUsername(u.getUsername())
                        .password(u.getPassword())
                        .roles(roles)
                        .disabled(!u.isActive())
                        .build();
                return ud;
            }).orElseThrow(() -> new RuntimeException("User not found: " + username));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http
    // .csrf(csrf -> csrf.disable()) // Cú pháp mới Spring Security 6
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/admin/**", "/api/**").hasAnyRole("ADMIN") // Cho phép
    // ADMIN truy cập API và
    // // Admin
    // .anyRequest().permitAll())
    // .formLogin(form -> form
    // .loginPage("/login") // Custom trang login nếu có
    // .successHandler(customSuccessHandler())
    // .permitAll())
    // .logout(logout -> logout
    // .logoutSuccessUrl("/"))
    // .exceptionHandling(ex -> ex
    // .accessDeniedPage("/access-denied"));

    // return http.build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/video/image/**").permitAll()
                        .requestMatchers("/admin/**", "/api/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response,
                org.springframework.security.core.Authentication authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ADMIN"));
            if (isAdmin) {
                // Chuyển hướng về trang quản lý Video AJAX
                response.sendRedirect(request.getContextPath() + "/admin/videos-ajax");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        };
    }
}