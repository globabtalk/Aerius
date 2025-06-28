package com.Voice.Aerius.config;

import com.Voice.Aerius.Auth.service.CustomUserDetailsService;
import com.Voice.Aerius.security.JwtAuthenticationEntryPoint;
import com.Voice.Aerius.security.JwtAuthenticationFilter;
import com.Voice.Aerius.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Getter
    private final ObjectMapper objectMapper;

    @Bean
    public JwtAuthenticationFilter filter(
            JwtTokenProvider jwtTokenProvider,
             CustomUserDetailsService customUserDetailsService,
             ObjectMapper objectMapper){
        return new JwtAuthenticationFilter(
                jwtTokenProvider,customUserDetailsService,objectMapper);

    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configurationn = new CorsConfiguration();
        configurationn.setAllowCredentials(true);
        configurationn.addAllowedOrigin("*");
        configurationn.addAllowedHeader("*");
        configurationn.addAllowedMethod("*");
        corsConfigurationSource.registerCorsConfiguration("/**", configurationn);
        return corsConfigurationSource;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security,
            JwtAuthenticationFilter filter)throws Exception{
        security.csrf(AbstractHttpConfigurer::disable)
                .cors(crs->crs.configurationSource(configurationSource()))
                .authorizeHttpRequests(authEntry->authEntry.requestMatchers("/","/docs")
                        .permitAll().requestMatchers("/api").authenticated().anyRequest()
                        .authenticated()).exceptionHandling(exception->exception
                        .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
