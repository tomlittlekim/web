package com.egovframework.emes.domain.user.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor // 기본 생성자
@EqualsAndHashCode // equals()와 hashCode() 자동 생성
public class UserId implements Serializable {

  private String site;
  private String userId;

  // 생성자
  public UserId(String site, String userId) {
    if (site == null || site.isBlank()) {
      throw new IllegalArgumentException("Site cannot be null or empty.");
    }
    if (userId == null || userId.isBlank()) {
      throw new IllegalArgumentException("User ID cannot be null or empty.");
    }
    this.site = site;
    this.userId = userId;
  }
}
