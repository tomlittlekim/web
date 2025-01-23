package com.egovframework.emes.domain.user;


import com.egovframework.emes.domain.user.id.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MOS_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @EmbeddedId
  private UserId id;

  @Column(name = "USER_NAME", length = 40)
  private String userName;

  @Column(name = "JIK_GUB", length = 40)
  private String jikGub;

  @Column(name = "CELL_NUMBER", length = 40)
  private String cellNumber;

  @Column(name = "ADDRESS1", length = 40)
  private String address1;

  @Column(name = "ADDRESS2", length = 40)
  private String address2;

  @Column(name = "EMAIL_ADDRESS", length = 200)
  private String emailAddress;

  @Column(name = "PASSWORD", length = 40)
  private String password;

  @Column(name = "USER_CLASS_ID", length = 40)
  private String userClassId;

  @Column(name = "COMMENTS", length = 2000)
  private String comments;

  @Column(name = "EXECUTE_SERVICE", length = 200)
  private String executeService;

  @Column(name = "ORIGINAL_EXECUTE_SERVICE", length = 200)
  private String originalExecuteService;

  @Column(name = "ACTION_CODE", length = 40)
  private String actionCode;

  @Column(name = "IS_USABLE", length = 10)
  private String isUsable;

  @Column(name = "CREATE_USER", length = 40)
  private String createUser;

  @Column(name = "CREATE_TIME", updatable = false)
  private LocalDateTime createTime;

  @Column(name = "UPDATE_USER", length = 40)
  private String updateUser;

  @Column(name = "UPDATE_TIME")
  private LocalDateTime updateTime;

  @Column(name = "LAST_EVENT_TIME", length = 40)
  private String lastEventTime;
}
