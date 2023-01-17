package com.example.itraining_api.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.itraining_api.filter.CsrfCookieSecurityConfig;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.securityContext().requireExplicitSave(false)
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                        configuration.setAllowedMethods(Arrays.asList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        return configuration;
                    }
                }).and().csrf().ignoringRequestMatchers("/contact", "/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterAfter(new CsrfCookieSecurityConfig(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests().anyRequest().permitAll()
                // .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
                // // , "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
                // .requestMatchers("/notices", "/contact").permitAll()
                .and().formLogin()
                .and().httpBasic();
        // all request granted
        // http.authorizeHttpRequests().anyRequest().permitAll()
        // .and().formLogin()
        // .and().httpBasic();
        // all request denied
        // http.authorizeHttpRequests().anyRequest().denyAll()
        // .and().formLogin()
        // .and().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    // UserDetails admin = User.withDefaultPasswordEncoder()
    // .username("admin")
    // .password("admin")
    // .authorities("admin").build();

    // return new InMemoryUserDetailsManager(admin);
    // }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    // UserDetails user = User.withUsername("user")
    // .password("admin")
    // .authorities("read").build();

    // return new InMemoryUserDetailsManager(user);
    // }

    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource){
    // return new JdbcUserDetailsManager(dataSource);
    // }
}
