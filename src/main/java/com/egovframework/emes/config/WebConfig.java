package com.egovframework.emes.config;

import com.egovframework.emes.common.interceptor.AuthenticInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final AuthenticInterceptor authenticInterceptor;

  public WebConfig(AuthenticInterceptor authenticInterceptor) {
    this.authenticInterceptor = authenticInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticInterceptor)
        .addPathPatterns("/**") // 모든 경로에 적용
        .excludePathPatterns("/login", "/error"); // 로그인 및 에러 페이지는 제외
  }
}
