package com.example.springboottest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.springboottest.domain.Notice;

public interface NoticeSearch {
  Page<Notice> searchAll(String keyword, Pageable pageable);
}
