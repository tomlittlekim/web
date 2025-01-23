package com.egovframework.emes.common.jwt;

import com.egovframework.emes.dto.ResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -5180902194184255251L;

  // JWT 토큰 유효 시간 (1시간)
  private static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;

  private final SecretKey secretKey;

  public JwtTokenUtil(@Value("${jwt.secret-key}") String secretKeyString) {
    this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
  }

  /**
   * JWT에서 SITE 추출
   */
  public String getFieldValueFromToken(String token, String field) {
    return getClaimFromToken(token, claims -> claims.get(field).toString());
  }

  /**
   * JWT에서 Claims 전체 추출
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)  // HMAC 또는 RSA/EC 서명 키
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * JWT에서 특정 Claims 추출
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * JWT가 만료되었는지 확인
   */
  public boolean isTokenExpired(String token) {
    final Date expiration = getClaimFromToken(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  /**
   * JWT 생성
   */
  public String generateToken(ResponseDto response) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("site", response.getSite());
    claims.put("id", response.getId());
    claims.put("name", response.getUserName());
    claims.put("jikGub", response.getJikGub());
    claims.put("userClassId", response.getUserClassId());
    return doGenerateToken(claims, "Authorization");
  }

  /**
   * JWT 생성 로직
   */
  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * JWT가 유효한지 확인
   */
  public boolean isTokenValid(String token, String username) {
    final String extractedUsername = getFieldValueFromToken(token, "id");
    return extractedUsername.equals(username) && !isTokenExpired(token);
  }
}