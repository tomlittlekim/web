package com.egovframework.emes.config;

import com.egovframework.emes.common.jwt.JwtAuthenticationEntryPoint;
import com.egovframework.emes.common.jwt.JwtAuthenticationFilter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final List<String> AUTH_GET_WHITELIST = List.of(
      "/mainPage", // 메인 화면 리스트 조회
      "/board", // 게시판 목록 조회
      "/board/{bbsId}/{nttId}", // 게시물 상세 조회
      "/boardFileAtch/{bbsId}", // 게시판 파일 첨부 가능 여부 조회
      "/schedule/daily", // 일별 일정 조회
      "/schedule/week", // 주간 일정 조회
      "/schedule/{schdulId}", // 일정 상세 조회
      "/image" // 갤러리 이미지 보기
  );

  private static final List<String> AUTH_WHITELIST = List.of(
      "/", "/login/**", "/auth/login-jwt", "/auth/login", "/file",
      "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
      "/swagger-ui.html", "/swagger-ui/**"
  );

  private static final List<String> ORIGINS_WHITELIST = List.of(
      "http://localhost:3000"
  );

  @Bean
  public JwtAuthenticationFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH"));
    configuration.setAllowedOrigins(ORIGINS_WHITELIST);
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(AUTH_WHITELIST.toArray(new String[0])).permitAll()
            .requestMatchers(HttpMethod.GET, AUTH_GET_WHITELIST.toArray(new String[0])).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .build();
  }

}

