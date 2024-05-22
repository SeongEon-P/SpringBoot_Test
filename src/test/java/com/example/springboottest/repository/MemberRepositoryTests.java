package com.example.springboottest.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.springboottest.domain.Member;
import com.example.springboottest.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void insertMember(){
    //1~100까지 반복하기 위한 스트림
    IntStream.rangeClosed(1,100).forEach(i->{
      //회원 생성 1~100
      Member member = Member.builder()
          .mid("member"+i)
          //비밀번호 암호화
          .mpw(passwordEncoder.encode("1111"))
          .email("email"+i+"@aaa.bbb")
          .build();
      //일반 회원의 권한 설정
      member.addRole(MemberRole.USER);
      if(i >= 90){
        //관리자 권한 설정
        member.addRole(MemberRole.ADMIN);
      }
      //데이터베이스에 저장
      memberRepository.save(member);
    });
  }
  @Test
  public void testRead(){
    //데이터 베이스에서 mid를 기준으로 데이터를 취득
    Optional<Member> result = memberRepository.getWithRoles("member100");
    //에러 확인
    Member member = result.orElseThrow();
    //전체 데이터 출력
    log.info(member);
    //role데이터 출력
    log.info(member.getRoleSet());
    member.getRoleSet().forEach(memberRole -> {
      log.info(memberRole.name());
    });
  }
}












