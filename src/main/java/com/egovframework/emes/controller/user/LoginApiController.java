package com.egovframework.emes.controller.user;

import com.egovframework.emes.common.dto.ResultResponse;
import com.egovframework.emes.common.enums.ResultCode;
import com.egovframework.emes.dto.RequestDto;
import com.egovframework.emes.service.user.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "LoginApiController", description = "로그인 관련")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginApiController {

  private final LoginService loginService;

  @Operation(
      summary = "일반 로그인",
      description = "일반 로그인 처리",
      tags = {"LoginApiController"}
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그인 성공"),
      @ApiResponse(responseCode = "300", description = "로그인 실패")
  })
  @PostMapping(value = "/login", consumes = "application/json")
  public HashMap<String, Object> actionLogin(@RequestBody RequestDto requestDto,
      HttpServletRequest request)
      throws Exception {
    // 1. 일반 로그인 처리
    HashMap<String, Object> result = loginService.actionLogin(requestDto);
    // 2. 로그인 성공 시 세션에 loginResponse 저장
    if ("200".equals(result.get("resultCode"))) {
      // resultCode가 200이면 loginResponse를 세션에 저장
      Object responseDto = result.get("responseDto");
      if (responseDto != null) {
        request.getSession().setAttribute("responseDto", responseDto);
      }
    }

    return result;
  }

  @Operation(
      summary = "JWT 로그인",
      description = "JWT 로그인 처리",
      tags = {"LoginApiController"}
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그인 성공"),
      @ApiResponse(responseCode = "300", description = "로그인 실패")
  })
  @PostMapping(value = "/login-jwt", consumes = "application/json")
  public HashMap<String, Object> actionLoginJwt(@RequestBody RequestDto requestDto,
      HttpServletRequest request) throws Exception {
    // 1. 일반 로그인 처리
    HashMap<String, Object> result = loginService.actionLoginJwt(requestDto);
    // 2. 로그인 성공 시 세션에 loginResponse 저장
    if ("200".equals(result.get("resultCode"))) {
      // resultCode가 200이면 loginResponse를 세션에 저장
      Object responseDto = result.get("responseDto");
      if (responseDto != null) {
        request.getSession().setAttribute("responseDto", responseDto);
      }
    }

    return result;
  }

  @Operation(
      summary = "로그아웃",
      description = "로그아웃 처리(JWT, 일반 관계 없이)",
      security = {@SecurityRequirement(name = "Authorization")},
      tags = {"LoginApiController"}
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그아웃 성공")
  })
  @GetMapping(value = "/logout")
  public ResultResponse actionLogoutJson(HttpServletRequest request,
      HttpServletResponse response) {
    new SecurityContextLogoutHandler().logout(request, response, null);

    return new ResultResponse(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
  }
}

