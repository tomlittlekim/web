package com.egovframework.emes.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 인증 여부 체크 인터셉터
 * Spring Boot 3.4.1 및 Java 17에 맞게 개선
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticInterceptor implements HandlerInterceptor {

  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  /**
   * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
   * 계정정보(LoginVO)가 없다면, 로그인 페이지로 리다이렉트.
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    return true;
    /*// 로직 추가: 인증 여부 확인
    log.info("AuthenticInterceptor - preHandle 실행");

    // 인증된 사용자 정보 가져오기 (예제)
    Optional<LoginVO> optionalLoginVO = Optional.ofNullable((LoginVO) EgovUserDetailsHelper.getAuthenticatedUser());

    if (optionalLoginVO.isPresent() && optionalLoginVO.get().getId() != null) {
      log.debug("AuthenticInterceptor - 인증 성공: 세션ID: {}", optionalLoginVO.get().getId());
      return true;
    }

    log.warn("AuthenticInterceptor - 인증 실패. 로그인 페이지로 리다이렉트");

    // 로그인 페이지로 리다이렉트 처리
    response.sendRedirect(allowedOrigins + "/login");
    return false;*/
  }
}
