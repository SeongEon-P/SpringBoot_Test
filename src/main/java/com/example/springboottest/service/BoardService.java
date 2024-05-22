package com.example.springboottest.service;

import com.example.springboottest.domain.Board;
import com.example.springboottest.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {
  Long register(BoardDTO boardDTO);
  BoardDTO readOne(Long bno);
  void modify(BoardDTO boardDTO);
  void remove(Long bno);
  PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
  // 게시글 목록에 댓글 갯수 표시 하기.
  PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
  PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

  // boardDTO를 board 객체로 바꾸기위해 사용하는 메서드
  default Board dtoToEntity(BoardDTO boardDTO) {
    Board board = Board.builder()
        .bno(boardDTO.getBno())
        .title(boardDTO.getTitle())
        .content(boardDTO.getContent())
        .writer(boardDTO.getWriter())
        .build();
    // boardDTO의 String[] 타입을 Set<BoardImage>타입으로 바꾸는 if문
    if(boardDTO.getFileNames() != null){
      // 반복문으로 String[]의 문자열을 하나씩 변경
      boardDTO.getFileNames().forEach(fileName ->{
        // uuid_파일이름.확장자 -> arr[0] = uuid, arr[1] = 파일이름.확장자
        String[] arr = fileName.split("_",2);
        board.addImage(arr[0], arr[1]);
      });
    }
    return board;
  }
  //board를 boardDTO로 변환하기위한 메서드
  default BoardDTO entityToDto(Board board) {
    //단순 데이터 설정
    BoardDTO boardDTO = BoardDTO.builder()
        .bno(board.getBno())
        .title(board.getTitle())
        .content(board.getContent())
        .writer(board.getWriter())
        .regDate(board.getRegDate())
        .modDate(board.getModDate())
        .build();
    //Set<boardImage>을  List<String>로 변환하기위한 코드
    List<String> fileNames = board.getImageSet().stream().sorted()
        // uuid_파일명.확장자 형식으로 boardImage데이터를 String 타입 변환
        .map(boardImage -> boardImage.getUuid()+"_"+boardImage.getFileName())
        //변환한 결과값을 합쳐서 List타입으로 반환
        .collect(Collectors.toList());
    boardDTO.setFileNames(fileNames);
    return boardDTO;
  }
}
















