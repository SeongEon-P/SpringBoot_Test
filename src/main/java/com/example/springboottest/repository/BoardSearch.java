package com.example.springboottest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.springboottest.domain.Board;
import com.example.springboottest.dto.BoardListAllDTO;
import com.example.springboottest.dto.BoardListReplyCountDTO;

public interface BoardSearch {
  Page<Board> search1(Pageable pageable);
  Page<Board> searchAll(String[] types, String keyword, Pageable pageable);
  // 댓글 표시해서 목록 출력하기.
  Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

  //모든 데이터를 조회하기
  Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}
