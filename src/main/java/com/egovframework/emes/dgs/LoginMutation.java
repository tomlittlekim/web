package com.egovframework.emes.dgs;

import com.egovframework.emes.dto.RequestDto;
import com.egovframework.emes.dto.ResponseDto;
import com.egovframework.emes.service.user.LoginService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;

//@DgsComponent
//@RequiredArgsConstructor
//public class LoginMutation {
//
//  private final LoginService loginService;
//
//  @DgsMutation
//  public ResponseDto loginJwt(RequestDto requestDto) throws Exception {
//    HashMap<String, Object> result = loginService.actionLoginJwt(requestDto);
//
//    String resultCode = result.getOrDefault("resultCode", "").toString();
//    String responseDto = result.getOrDefault("responseDto", "").toString();
//
//    return new ResponseDto(resultCode, responseDto);
//  }
//}
