package com.egovframework.emes.common.enums;

public enum ResultCode {

  SUCCESS(200, "성공했습니다."),
  AUTH_ERROR(403, "인가된 사용자가 아닙니다."),
  DELETE_ERROR(700, "삭제 중 내부 오류가 발생했습니다."),
  SAVE_ERROR(800, "저장시 내부 오류가 발생했습니다."),
  INPUT_CHECK_ERROR(900, "입력값 무결성 오류 입니다.");

  private final int code;
  private final String message;

  ResultCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  // Getter methods
  public int code() {
    return code;
  }

  public String message() {
    return message;
  }

  // Using Java 17's record to encapsulate code and message as a data structure
  public record CodeMessage(int code, String message) {}

  // Method to return a CodeMessage record
  public CodeMessage asRecord() {
    return new CodeMessage(code, message);
  }

  // Java 17's switch expression to get a message by code
  public static String getMessageByCode(int code) {
    return switch (code) {
      case 200 -> "성공했습니다.";
      case 403 -> "인가된 사용자가 아닙니다.";
      case 700 -> "삭제 중 내부 오류가 발생했습니다.";
      case 800 -> "저장시 내부 오류가 발생했습니다.";
      case 900 -> "입력값 무결성 오류 입니다.";
      default -> "알 수 없는 코드입니다.";
    };
  }
}
