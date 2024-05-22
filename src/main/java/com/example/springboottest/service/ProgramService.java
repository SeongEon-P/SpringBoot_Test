package com.example.springboottest.service;

import com.example.springboottest.dto.ProgramDTO;

import java.util.List;

public interface ProgramService {
  List<ProgramDTO> selectAll();
}
