package com.egovframework.emes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청을 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Site는 필수 항목입니다.")
    private String site;
    private String id;
    private String password;
    private String name;

}
