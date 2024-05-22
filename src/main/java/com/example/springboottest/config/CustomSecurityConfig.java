package com.example.springboottest.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.example.springboottest.security.CustomUserDetailsService;
import com.example.springboottest.security.handler.Custom403Handler;
import com.example.springboottest.security.handler.CustomSocialLoginSuccessHandler;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {
  private final DataSource dataSource;
  private final CustomUserDetailsService customUserDetailsService;

  @Bean
  //비밀번호 암호화 타입 설정
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  //소셜 로그인이 성공했을 때의 설정
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new CustomSocialLoginSuccessHandler(passwordEncoder());
  }
  @Bean
  //로그인 순서 및 실행 방식을 설정하는 메서드
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.info("-------------------configure---------------------");
    //폼태그로 로그인을 하도록 설정하겠다.
    http.formLogin().loginPage("/member/login");
    //csrf 인증 방식을 사용하지 않음
    http.csrf().disable();
    //자동 로그인 설정 - 쿠키 생성
    http.rememberMe()
        .key("12345678")
        .tokenRepository(persistentTokenRepository())
        .userDetailsService(customUserDetailsService)
        .tokenValiditySeconds(60*60*24*30);
    //에러 발생시의 처리 설정
    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    //oauth2 소셜 로그인 설정
    http.oauth2Login()
        .loginPage("/member/login")
        .successHandler(authenticationSuccessHandler());

    return http.build();
  }
  @Bean
  //접속 거부시의 처리
  AccessDeniedHandler accessDeniedHandler() {
    return new Custom403Handler();
  }
  @Bean
  //여러가지 웹 시큐리티
  public WebSecurityCustomizer webSecurityCustomizer() {
    log.info("-----------WebSecurityCustomizer-----------");
    //static 폴더 안에 존재하는 정적리소스는 로그인 과정을 거치지 않고 실행 가능
    return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  //토큰 관련 데이터베이스 설정 (자동 로그인)
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
    repo.setDataSource(dataSource);
    return repo;
  }


}
