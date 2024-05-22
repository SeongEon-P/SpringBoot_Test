package com.example.springboottest.service;

import com.example.springboottest.dto.NoticeDTO;
import com.example.springboottest.dto.PageRequestDTO;
import com.example.springboottest.dto.PageResponseDTO;

public interface NoticeService {
  Long register(NoticeDTO noticeDTO);
  NoticeDTO readOne(Long no);
  void modify(NoticeDTO noticeDTO);
  void remove(Long no);
  PageResponseDTO<NoticeDTO> list(PageRequestDTO pageRequestDTO);
}
















