package com.example.springboottest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.springboottest.domain.Member;
import com.example.springboottest.domain.MemberRole;
import com.example.springboottest.repository.MemberRepository;
import com.example.springboottest.security.dto.MemberSecurityDTO;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("userRequest....");
    log.info(userRequest);

    log.info("oauth2 user...........");

    ClientRegistration clientRegistration = userRequest.getClientRegistration();

    String clientName = clientRegistration.getClientName();
    log.info(clientName);
    OAuth2User oAuth2User = super.loadUser(userRequest);
    Map<String, Object> paramMap = oAuth2User.getAttributes();
    log.info(paramMap);

    String email = null;

    switch(clientName){
      case "kakao":
        email = getKakaoNickName(paramMap);
//        email = getKakaoEmail(paramMap);
        break;
    }
    log.info("==========================");
    log.info(email);

    return generateDTO(email,paramMap);
  }
  private MemberSecurityDTO generateDTO(String email,Map<String, Object> params){
    Optional<Member> result = memberRepository.findByEmail(email);
    if(result.isEmpty()){
      Member member = Member.builder()
              .mid(email)
              .mpw(passwordEncoder.encode("1111"))
              .email(email)
              .social(true)
              .build();
      member.addRole(MemberRole.USER);
      memberRepository.save(member);
      MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO
              (email,"1111",email,"", "", false,true,
                      Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
      memberSecurityDTO.setProps(params);
      return memberSecurityDTO;
    }else{
      Member member = result.get();
      MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
              member.getMid(),
              member.getMpw(),
              member.getEmail(),
              member.getName(),
              member.getAddr(),
              member.isDel(),
              member.isSocial(),
              member.getRoleSet().stream().map(memberRole-> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())
      );
      return memberSecurityDTO;
    }

  }
  private String getKakaoEmail(Map<String, Object> paramMap){
    log.info("Kakao--------------------");
    Object value = paramMap .get("kakao_account");
    log.info(value);
    LinkedHashMap accountMap = (LinkedHashMap)value;
    String email = (String)accountMap.get("email");
    log.info("email..."+email);
    return email;
  }

  private String getKakaoNickName(Map<String, Object> paramMap){
    log.info("Kakao--------------------");
    //kakao에서 받은 데이터 중에 닉네임이 들어있는 map의 key
    Object value = paramMap.get("properties");
    log.info(value);
    LinkedHashMap accountMap = (LinkedHashMap)value;
    String nickname = (String)accountMap.get("nickname");
    log.info("nickname..."+nickname);
    return nickname;
  }
}















