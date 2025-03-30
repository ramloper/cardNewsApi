package ac.yongson.artgram.global.security;

import ac.yongson.artgram.global.auth.JwtTokenAuthFilter;
import ac.yongson.artgram.global.auth.MyFilterResponse;
import ac.yongson.artgram.global.exception.Exception401;
import ac.yongson.artgram.global.exception.Exception403;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustom -> corsCustom.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionedCreationPolicy -> sessionedCreationPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handlingConfigurer -> {
                    // 인증 실패 처리
                    handlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                        log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : " + authException.getMessage());
                        MyFilterResponse.unAuthorized(response, new Exception401("인증되지 않았습니다"));
                    });

                    // 권한 실패 처리
                    handlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                        log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : " + accessDeniedException.getMessage());
                        MyFilterResponse.forbidden(response, new Exception403("권한이 없습니다"));
                    });
                })
                .authorizeHttpRequests(request -> request
//                        .requestMatchers(HttpMethod.GET,"/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/**").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN","MANAGER")
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/admin/**").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenAuthFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://172.30.1.96:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
