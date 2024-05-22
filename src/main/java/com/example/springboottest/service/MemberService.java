package com.example.springboottest.service;

import com.example.springboottest.dto.MemberJoinDTO;

public interface MemberService {
  static class MidExistException extends Exception {}
  void join(MemberJoinDTO member) throws MidExistException;
  void modify(MemberJoinDTO memberJoinDTO);
  void deleteAccount(String mid);
  boolean confirm(MemberJoinDTO memberJoinDTO);
}

