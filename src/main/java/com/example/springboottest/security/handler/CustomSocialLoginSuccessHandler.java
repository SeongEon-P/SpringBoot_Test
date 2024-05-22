package com.example.springboottest.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.example.springboottest.security.dto.MemberSecurityDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
  private final PasswordEncoder passwordEncoder;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    log.info("-------------------------------------------------");
    log.info("CustomSocialLoginSuccessHandler onAuthenticationSuccess");
    //로그인 시의 데이터 확인
    log.info(authentication.getPrincipal());
    // 로그인 데이터를 MemberSecurityDTO로 변환
    MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
    //비밀번호를 변수에 저장
    String encodedPw = memberSecurityDTO.getMpw();
    //소셜계정 확인
    if(memberSecurityDTO.isSocial() &&
        //비밀번호가 1111인지 확인   || 암호 해독시의 비밀번호가 1111인지 확인
        (encodedPw.equals("1111") || passwordEncoder.matches("1111", encodedPw))) {
      log.info("Should Change Password");
      log.info("Redirect to Member Modify");
      // 회원 수정 페이지로 이동
      response.sendRedirect("/member/modify?mid="+memberSecurityDTO.getMid());
      return;
    }else{
      //게시판 화면으로 이동
      response.sendRedirect("/board/list");
    }
  }
}
