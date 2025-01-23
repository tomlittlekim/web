package com.egovframework.emes.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to support logging information using SLF4J
 *
 * @author Vincent Han
 * @since 2014.09.18
 * @version 2.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2014.09.18	표준프레임워크센터	최초 생성
 *   2023.01.22    개선 작성      최신 Spring Boot 및 SLF4J 적용
 *
 * </pre>
 */
@Slf4j
public final class BasicLogger {

  // Prevent instantiation
  private BasicLogger() {
    throw new UnsupportedOperationException("BasicLogger is a utility class and cannot be instantiated.");
  }

  /**
   * 기록이나 처리가 불필요한 경우 사용.
   * @param message 메시지
   * @param exception 예외
   */
  public static void ignore(String message, Throwable exception) {
    if (exception == null) {
      log.debug("IGNORE: {}", message);
    } else {
      log.debug("IGNORE: {}", message, exception);
    }
  }

  /**
   * 기록이나 처리가 불필요한 경우 사용.
   * @param message 메시지
   */
  public static void ignore(String message) {
    ignore(message, null);
  }

  /**
   * 디버그 정보를 기록하는 경우 사용.
   * @param message 메시지
   * @param exception 예외
   */
  public static void debug(String message, Throwable exception) {
    if (exception == null) {
      log.debug(message);
    } else {
      log.debug(message, exception);
    }
  }

  /**
   * 디버그 정보를 기록하는 경우 사용.
   * @param message 메시지
   */
  public static void debug(String message) {
    log.debug(message);
  }

  /**
   * 일반적인 정보를 기록하는 경우 사용.
   * @param message 메시지
   */
  public static void info(String message) {
    log.info(message);
  }
}