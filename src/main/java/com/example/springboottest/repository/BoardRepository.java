package com.example.springboottest.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.springboottest.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch{
  @Query(value = "SELECT now()", nativeQuery = true)
  String getTime();

  @EntityGraph(attributePaths = {"imageSet"})
  @Query("SELECT b FROM Board b WHERE b.bno =:bno")
  Optional<Board> findByIdWithImages(Long bno);
}
