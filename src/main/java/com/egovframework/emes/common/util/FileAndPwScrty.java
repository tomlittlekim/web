package com.egovframework.emes.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * Base64 인코딩/디코딩을 통한 파일 암호화 및 복호화 유틸리티 클래스
 * 암호화된 비밀번호 처리 포함
 */
@Slf4j
@Component
public class FileAndPwScrty {

  private static final String FILE_SEPARATOR = File.separator;
  private static final int BUFFER_SIZE = 1024;

  /**
   * 파일 암호화
   *
   * @param source 암호화할 파일 경로
   * @param target 암호화된 파일 저장 경로
   * @return 암호화 성공 여부
   */
  public static boolean encryptFile(String source, String target) {
    try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(target))) {

      byte[] buffer = new byte[BUFFER_SIZE];
      int length;

      while ((length = input.read(buffer)) >= 0) {
        byte[] data = Arrays.copyOf(buffer, length);
        output.write(encodeBinary(data).getBytes());
        output.write(System.lineSeparator().getBytes());
      }

      return true;

    } catch (IOException e) {
      log.error("File encryption failed: {}", e.getMessage());
      return false;
    }
  }

  /**
   * 파일 복호화
   *
   * @param source 복호화할 파일 경로
   * @param target 복호화된 파일 저장 경로
   * @return 복호화 성공 여부
   */
  public static boolean decryptFile(String source, String target) {
    try (BufferedReader input = new BufferedReader(new FileReader(source));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(target))) {

      String line;
      while ((line = input.readLine()) != null) {
        output.write(decodeBinary(line));
      }

      return true;

    } catch (IOException e) {
      log.error("File decryption failed: {}", e.getMessage());
      return false;
    }
  }

  /**
   * 데이터 암호화 (Base64 인코딩)
   *
   * @param data 암호화할 데이터
   * @return 암호화된 문자열
   */
  public static String encodeBinary(byte[] data) {
    return Base64.encodeBase64String(data);
  }

  /**
   * 데이터 복호화 (Base64 디코딩)
   *
   * @param data 복호화할 문자열
   * @return 복호화된 데이터
   */
  public static byte[] decodeBinary(String data) {
    return Base64.decodeBase64(data);
  }

  /**
   * 비밀번호를 암호화하는 함수 (SHA-256 + Base64 인코딩)
   *
   * @param password 암호화할 비밀번호
   * @param id       암호화에 사용할 사용자 ID
   * @return 암호화된 비밀번호
   * @throws IllegalArgumentException 입력 값이 null이거나 빈 문자열인 경우
   */
  public static String encryptPassword(String password, String id) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("비밀번호는 null이거나 빈 값일 수 없습니다.");
    }

    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("ID는 null이거나 빈 값일 수 없습니다.");
    }

    try {
      // SHA-256 해시 인스턴스 생성
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      // ID를 먼저 업데이트하여 salt처럼 활용
      md.update(id.getBytes());

      // 비밀번호를 업데이트하여 해시 생성
      byte[] hash = md.digest(password.getBytes());

      // Base64 인코딩 후 반환
      return Base64.encodeBase64String(hash);
    } catch (Exception e) {
      log.error("비밀번호 암호화 실패: {}", e.getMessage());
      return null;
    }
  }


  /**
   * 비밀번호 검증
   *
   * @param password 입력된 원 비밀번호
   * @param encoded  암호화된 비밀번호
   * @param salt     암호화에 사용된 salt 값
   * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
   */
  /*public static boolean checkPassword(String password, String encoded, byte[] salt) {
    try {
      String hashedPassword = encryptPassword(password, salt);
      return hashedPassword != null && hashedPassword.equals(encoded);
    } catch (Exception e) {
      log.error("Password verification failed: {}", e.getMessage());
      return false;
    }
  }*/
}
