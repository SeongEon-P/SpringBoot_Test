package com.example.springboottest.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Program extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;
  @Column(length = 200, nullable = false)
  private String title;
  @Column(length = 300, nullable = false)
  private String text;
  @Column(length = 300, nullable = false)
  private String subtext;
  @Column(length = 100, nullable = false)
  private String schedule;
  private String img;
}
