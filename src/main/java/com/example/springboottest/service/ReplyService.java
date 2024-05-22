package com.example.springboottest.service;

import com.example.springboottest.dto.PageRequestDTO;
import com.example.springboottest.dto.PageResponseDTO;
import com.example.springboottest.dto.ReplyDTO;

public interface ReplyService {
  // 댓글 등록
  Long register(ReplyDTO replyDTO);
  // 댓글 읽기
  ReplyDTO read(Long rno);
  // 댓글 수정
  void modify(ReplyDTO replyDTO);
  // 댓글 삭제
  void remove(Long rno);
  //페이징 처리
  PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
