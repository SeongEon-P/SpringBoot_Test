package com.example.springboottest.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {
  private String mid;
  private String mpw;
  private String name;
  private String email;
  private String addr;
}
