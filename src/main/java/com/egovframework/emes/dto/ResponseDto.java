package com.egovframework.emes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String site;
  private String id;
  private String userName;
  private String jikGub;
  private String userClassId;

}
