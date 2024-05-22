package com.example.springboottest.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.info("-------------Access denied----------------");
    // 돌려줄 상태를 403으로 설정
    response.setStatus(HttpStatus.FORBIDDEN.value());
    //ContentType을 변수에 저장
    String contentType = request.getHeader("Content-Type");
    //ContentType의 종류가 json이면 true
    boolean jsonRequest = contentType.startsWith("application/json");
    log.info("isJSON : "+jsonRequest);
    //json 타입이 아닐 경우 login페이지로 이동
    if (!jsonRequest) {
      response.sendRedirect("/member/login?error=ACCESS_DENIED");
    }
  }
}
