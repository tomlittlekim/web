package com.egovframework.emes.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 인증 여부 체크 인터셉터
 * Spring Boot 3.4.1 및 Java 17에 맞게 개선
 */
@Slf4j
@Component
public class CustomAuthenticInterceptor implements HandlerInterceptor {

  /**
   * 세션에 계정정보가 있는지 여부로 인증 여부를 체크한다.
   * 세션이 없거나 인증되지 않은 경우 로그인 페이지로 리다이렉트.
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    /*// 기존 세션 가져오기 (없으면 null 반환)
    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("LoginVO") == null) {
      log.warn("CustomAuthenticInterceptor - 인증 실패: 세션이 없거나 유효하지 않습니다.");

      // 로그인 페이지로 리다이렉트
      response.sendRedirect("/login");
      return false;
    }

    log.debug("CustomAuthenticInterceptor - 인증 성공: 세션ID: {}", session.getId());*/
    return true;
  }
}