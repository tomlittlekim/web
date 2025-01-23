package com.egovframework.emes.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;

@Schema(description = "응답 객체 VO")
public record ResultResponse(
    @Schema(description = "응답 코드") int resultCode,
    @Schema(description = "응답 메시지") String resultMessage,
    Map<String, Object> result
) {

  /**
   * 기본 생성자: 기본값 설정
   */
  public ResultResponse() {
    this(0, "OK", new HashMap<>());
  }

  public ResultResponse(int code, String message) {
    this(code, message, new HashMap<>());
  }

  /**
   * 결과 추가 메서드
   */
  public ResultResponse putResult(String key, Object value) {
    result.put(key, value);
    return this;
  }

  /**
   * 결과 가져오기 메서드
   */
  public Object getResult(String key) {
    return result.get(key);
  }
}
