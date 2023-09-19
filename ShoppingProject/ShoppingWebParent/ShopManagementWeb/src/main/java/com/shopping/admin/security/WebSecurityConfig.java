package com.shopping.admin.security;

import com.shopping.admin.security.jwt.JwtTokenFilter;
import com.shopping.admin.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.exceptionHandling().authenticationEntryPoint(
//                ((request, response, authException) -> {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
//                            authException.getMessage());
//                })
//        );

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/login", "/api/docs/**").permitAll()
                .requestMatchers("/api/users/**", "/api/roles/**").hasAuthority("Admin")
                .requestMatchers("/api/categories", "/api/brands").hasAnyAuthority("Admin", "Editor")
                .requestMatchers("/api/products/new", "/api/products/delete/**").hasAnyAuthority("Admin", "Editor")
                .requestMatchers("/api/products/edit/**", "/api/products/save", "/api/products/check_unique").hasAnyAuthority("Admin", "Editor", "Salesperson")
                .requestMatchers("/api/products", "/api/products/", "/api/products/detail/**", "/api/products/page/**")
                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .requestMatchers("/api/products/**").hasAnyAuthority("Admin", "Editor")

//                .requestMatchers("/api/settings/**", "/api/countries/**", "/api/states/**").hasAuthority("Admin")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
