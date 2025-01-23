package com.egovframework.emes.common.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AopExceptionTransfer {

  private final ExceptionTransfer exceptionTransfer;

  /**
   * Pointcut 정의: egovframework의 서비스 구현체 내 모든 메서드
   */
  @Pointcut("execution(* com.egovframework.emes..impl.*Impl.*(..))")
  private void exceptionTransferService() {}

  /**
   * 예외 발생 후 처리
   * @param joinPoint AOP 조인 포인트
   * @param ex 발생한 예외
   */
  @AfterThrowing(pointcut = "exceptionTransferService()", throwing = "ex")
  public void doAfterThrowingExceptionTransferService(JoinPoint joinPoint, Exception ex)
      throws Exception {
    log.error("다음 메소드에서 Exception이 발생: {}", joinPoint.getSignature().toShortString(), ex);
    exceptionTransfer.transfer(joinPoint, ex);
  }
}