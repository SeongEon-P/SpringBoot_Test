package com.example.springboottest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboottest.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice,Long>, NoticeSearch {
}
