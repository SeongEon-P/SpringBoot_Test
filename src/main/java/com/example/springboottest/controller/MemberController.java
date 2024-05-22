package com.example.springboottest.controller;

import com.example.springboottest.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.springboottest.dto.MemberJoinDTO;
import com.example.springboottest.service.MemberService;

import java.util.Map;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/login")
  public void loginGET(String error, String logout){
    log.info("login get...............");
    log.info("logout: "+logout);
    if(logout != null){
      log.info("user logout...........");
    }
  }
  @GetMapping("/join")
  public void joinGET(){
    log.info("join get...............");
  }
  @PostMapping("/join")
  public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){
    log.info("join post...............");
    log.info(memberJoinDTO);
    try{
      //회원가입 서비스 실행
      memberService.join(memberJoinDTO);
      //아이디가 존재 할 경우 에러 발생
    }catch(MemberService.MidExistException e){
      //에러 발생시 리다이렉트 페이지에 error=mid 값을 가지고 이동
      redirectAttributes.addFlashAttribute("error","mid");
      return "redirect:/member/join";
    }
    redirectAttributes.addFlashAttribute("result","success");
    return "redirect:/board/list";
  }
  @GetMapping("/modify")
  //Principal : 로그인 정보가 모두 담겨있는 객체(아이디, 권한)
  public void modifyGET(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO, Model model){
    log.info("modify get...............");
    model.addAttribute("member", memberSecurityDTO);
    //괄호 안에 앞은 타임리프에서 사용할 이름, 뒤에는 데이터(값)
  }
  @PostMapping("/modify")
  public String modifyPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){
    log.info("modify POST...............");
    memberService.modify(memberJoinDTO);
    return "redirect:/board/list";
  }

  @PostMapping("/delete")
  public String deleteAccount(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
    String mid = memberJoinDTO.getMid();
    memberService.deleteAccount(mid);
    //계정 삭제 후 로그아웃, 로그아웃 하지않으면 삭제된 계정이 로그인 상태로 남게됨
    return "redirect:/logout";
  }

  @PostMapping("/confirm")
  public String confirmPOST(MemberJoinDTO memberJoinDTO, Model model) {
    log.info("ID check");
    boolean exist = memberService.confirm(memberJoinDTO);
    if(exist) {
      log.info("Yes");
      model.addAttribute("error", "mid");
    } else {
      log.info("No");
      model.addAttribute("success", "mid");
    }

    model.addAttribute("memberJoinDTO", memberJoinDTO); // 입력한 아이디를 다시 전달
    return "/member/join";
  }

}













