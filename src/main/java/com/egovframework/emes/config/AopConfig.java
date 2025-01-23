package com.egovframework.emes.config;

import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

  @Bean
  public ExceptionTransfer exceptionTransfer() {
    return new ExceptionTransfer();
  }
}
