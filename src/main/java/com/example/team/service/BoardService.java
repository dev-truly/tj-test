package com.example.team.service;

import com.example.team.entity.BoardAttach;
import com.example.team.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    int boardListCount(String boardCode, String searchType, String searchValue);

    List<BoardEntity> findBoardByPage(int from, int to, String boardCode, String searchType, String searchValue);

    BoardEntity findBoardDetail(Long boardNo);

    int deleteBoard(Long boardNo);

    BoardEntity saveBoard(BoardEntity board);

    Long findMaxRefbyBoardCode(String boardCode);

    int attachMinus(Long boardNo);
}
