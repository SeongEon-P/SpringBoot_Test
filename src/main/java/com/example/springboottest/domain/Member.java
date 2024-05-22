package com.example.springboottest.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {
  @Id
  private String mid;
  private String mpw;
  private String name;
  private String email;
  private String addr;
  private boolean del;
  private boolean social;


  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private Set<MemberRole> roleSet = new HashSet<>();

  public void changePassword(String mpw) {
    this.mpw = mpw;
  }
  public void changeEmail(String email) {
    this.email = email;
  }
  public void changeDel(boolean del) {
    this.del = del;
  }
  public void addRole(MemberRole role) {
    this.roleSet.add(role);
  }
  public void clearRoles(){
    this.roleSet.clear();
  }

}




















