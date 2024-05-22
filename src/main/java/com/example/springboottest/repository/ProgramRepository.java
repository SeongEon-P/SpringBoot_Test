package com.example.springboottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboottest.domain.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
}
