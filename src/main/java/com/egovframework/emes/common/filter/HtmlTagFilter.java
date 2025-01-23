package com.egovframework.emes.common.filter;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// Spring에서 관리하도록 설정
@WebFilter(urlPatterns = "/*") // URL 패턴에 따라 적용
@Component
public class HtmlTagFilter implements Filter {

  private static final Logger logger = Logger.getLogger(HtmlTagFilter.class.getName());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      logger.info("HTMLTagFilter가 다음과 같은 request를 승인함: " + ((HttpServletRequest) request).getRequestURI());
      chain.doFilter(new HtmlTagFilterRequestWrapper((HttpServletRequest) request), response);
    } else {
      logger.warning("Request가 HttpServletRequest의 인스턴스가 아님. 필터를 스킵함.");
      chain.doFilter(request, response);
    }
  }

  @Override
  public void init(FilterConfig config) {
    // 초기화가 필요하다면 추가 코드 작성 가능
    logger.info("HTMLTagFilter 초기화됨.");
  }

  @Override
  public void destroy() {
    // 필터 종료 시 자원 해제 등 작업
    logger.info("HTMLTagFilter 작업 종료 후 소멸.");
  }
}
