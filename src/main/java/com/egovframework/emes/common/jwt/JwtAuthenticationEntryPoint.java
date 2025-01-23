package com.egovframework.emes.common.jwt;

import com.egovframework.emes.common.dto.ResultResponse;
import com.egovframework.emes.common.enums.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  // ObjectMapper를 Spring에서 주입받음
  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    // ResultVO 생성 및 설정
    ResultResponse result = new ResultResponse(
        ResultCode.AUTH_ERROR.code(),
        ResultCode.AUTH_ERROR.message()
    );

    // JSON 변환
    String jsonResponse = objectMapper.writeValueAsString(result);

    // HTTP 응답 설정
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // 응답 데이터 작성
    response.getWriter().write(jsonResponse);

    // CORS 헤더 추가 (필요 시)
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
  }

}
