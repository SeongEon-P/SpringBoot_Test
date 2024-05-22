package com.example.springboottest.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.example.springboottest.domain.Notice;
import com.example.springboottest.domain.QNotice;

import java.util.List;

public class NoticeSearchImpl extends QuerydslRepositorySupport implements NoticeSearch {
  public NoticeSearchImpl(){super(Notice.class);}
  @Override
  public Page<Notice> searchAll(String keyword, Pageable pageable) {
    QNotice notice = QNotice.notice;
    JPQLQuery<Notice> query = from(notice);
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if(keyword != null){
      booleanBuilder.or(notice.title.contains(keyword));
      booleanBuilder.or(notice.content.contains(keyword));
      query.where(booleanBuilder);
    }
    query.where(notice.no.gt(0L));
    this.getQuerydsl().applyPagination(pageable, query);
    List<Notice> list = query.fetch();
    long count = query.fetchCount();
    return new PageImpl<>(list, pageable, count);
  }
}
