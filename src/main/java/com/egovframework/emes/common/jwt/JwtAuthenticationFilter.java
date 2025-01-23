package com.egovframework.emes.common.jwt;

import com.egovframework.emes.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private static final String HEADER_STRING = "Authorization";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // Step 1: Request Header에서 토큰을 가져온다.
    String jwtToken = request.getHeader(HEADER_STRING);
    if (jwtToken == null || jwtToken.isEmpty()) {
      log.debug("인증 헤더를 찾을 수 없거나 인증 헤더가 비어있음.");
      chain.doFilter(request, response);
      return;
    }

    // Step 2: 토큰의 유효성을 검사하고 사용자의 정보를 추출한다.
    try {
      String id = jwtTokenUtil.getFieldValueFromToken(jwtToken, "id");

      if (id == null) {
        log.debug("유효하지 않은 JWT Token: 사용자 ID를 찾을 수 없음.");
        chain.doFilter(request, response);
        return;
      }

      log.debug("JWT가 검증되었습니다. 사용자 아이디: {}", id);

      ResponseDto responseDto = new ResponseDto(
          jwtTokenUtil.getFieldValueFromToken(jwtToken, "site"),
          id,
          jwtTokenUtil.getFieldValueFromToken(jwtToken, "userName"),
          jwtTokenUtil.getFieldValueFromToken(jwtToken, "jikGub"),
          jwtTokenUtil.getFieldValueFromToken(jwtToken, "userClassId")
      );

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          responseDto,
          null,
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
      );
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // Step 3: Context에 인증정보를 저장한다.
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (ExpiredJwtException e) {
      log.warn("JWT 토큰이 만료되었습니다: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.warn("지원하지 않는 JWT 토큰: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.warn("비정상적인 JWT 토큰: {}", e.getMessage());
    } catch (SignatureException e) {
      log.warn("JWT 토큰 서명 검증 실패: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.warn("JWT 토큰이 유효하지 않습니다: {}", e.getMessage());
    }

    // Step 4: 필터 체인을 계속한다.
    chain.doFilter(request, response);
  }
}
