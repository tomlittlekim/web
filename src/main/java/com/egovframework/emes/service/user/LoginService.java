package com.egovframework.emes.service.user;

import com.egovframework.emes.common.jwt.JwtTokenUtil;
import com.egovframework.emes.common.util.FileAndPwScrty;
import com.egovframework.emes.domain.user.id.UserId;
import com.egovframework.emes.dto.RequestDto;
import com.egovframework.emes.dto.ResponseDto;
import com.egovframework.emes.repository.user.UserRepository;
import java.util.HashMap;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService extends EgovAbstractServiceImpl {

  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public LoginService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public HashMap<String, Object> actionLogin(RequestDto requestDto) {
    HashMap<String, Object> result = new HashMap<>();

    // 비밀번호 암호화
    String enPassword = FileAndPwScrty.encryptPassword(
        requestDto.getId(),
        requestDto.getPassword()
    );

    // 사용자 조회 및 처리
    userRepository.findByIdAndPassword(
        new UserId(requestDto.getSite(), requestDto.getId()),
        enPassword
    ).ifPresentOrElse(user -> {
      // 로그인 성공 시 LoginResponse 생성
      ResponseDto responseDto = new ResponseDto(
          user.getId().getSite(),
          user.getId().getUserId(),
          user.getUserName(),
          user.getJikGub(),
          user.getUserClassId()
      );
      result.put("responseDto", responseDto);
      result.put("resultCode", "200");
      result.put("resultMessage", "로그인 성공");
    }, () -> {
      // 로그인 실패 시 처리
      result.put("resultCode", "300");
      result.put("resultMessage", "로그인 실패. 사용자 정보가 일치하지 않습니다.");
    });

    return result;
  }

  public HashMap<String, Object> actionLoginJwt(RequestDto requestDto)
      throws UsernameNotFoundException {
    HashMap<String, Object> result = new HashMap<>();

    // 비밀번호 암호화
    String enPassword = FileAndPwScrty.encryptPassword(
        requestDto.getId(),
        requestDto.getPassword()
    );

    // 사용자 조회 및 처리
    userRepository.findByIdAndPassword(
        new UserId(requestDto.getSite(), requestDto.getId()),
        enPassword
    ).ifPresentOrElse(user -> {
      // 로그인 성공 처리
      ResponseDto responseDto = new ResponseDto(
          user.getId().getSite(),
          user.getId().getUserId(),
          user.getUserName(),
          user.getJikGub(),
          user.getUserClassId()
      );

      // JWT 토큰 생성
      String jwtToken = jwtTokenUtil.generateToken(responseDto);

      // 결과 설정
      result.put("jToken", jwtToken);
      result.put("resultCode", "200");
      result.put("resultMessage", "로그인 성공");
      result.put("responseDto", responseDto);

    }, () -> {
      // 로그인 실패 처리
      result.put("resultCode", "300");
      result.put("resultMessage", "로그인 실패. 사용자 정보가 일치하지 않습니다.");
    });

    return result;
  }

}
