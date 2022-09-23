package com.example.team.service;

import com.example.team.common.GenericSpecification;
import com.example.team.entity.BoardEntity;
import com.example.team.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Override
    public int boardListCount(String boardCode, String searchType, String searchValue) {
        GenericSpecification<BoardEntity> boardSpec = new GenericSpecification<>();
        Specification<BoardEntity> spec = getBoardCodeList(boardCode);
        if (!searchValue.isEmpty()) {
            spec = spec.and(
                    boardSpec.like(searchType, searchValue)
            );
        }

        return boardRepository.count(spec);
    }

    @Override
    public List<BoardEntity> findBoardByPage(int from, int to, String boardCode, String searchType, String searchValue) {
        GenericSpecification<BoardEntity> boardSpec = new GenericSpecification<>();
        Specification<BoardEntity> spec = getBoardCodeList(boardCode);

        if (!searchValue.isEmpty()) {
            spec = spec.and(
                    boardSpec.like(searchType, searchValue)
            );
        }

        Sort sort = Sort.by("ref").descending().and(
                Sort.by("step").ascending()
        );
        Pageable pageable = PageRequest.of(from, to, sort);

        return boardRepository.findAll(spec, pageable);
    }

    public Specification<BoardEntity> getBoardCodeList(String boardCode) {
        GenericSpecification<BoardEntity> boardSpec = new GenericSpecification<>();

        return Specification.where(
                boardSpec.equal("boardCode", boardCode)
                    .and(
                            boardSpec.equal("delete", "n")
                    )
                );
    }

    public BoardEntity findBoardDetail(Long boardNo) {
        return boardRepository.findByBoardNoAndDelete(boardNo, "n");
    }

    @Override
    public int deleteBoard(Long boardNo) {
        return boardRepository.deleteBoard(boardNo);
    }

    @Override
    public BoardEntity saveBoard(BoardEntity board) {
        return boardRepository.save(board);
    }

    @Override
    public Long findMaxRefbyBoardCode(String boardCode) {
        return boardRepository.maxRefbyBoardCode(boardCode);
    }

    @Override
    public int attachMinus(Long boardNo) {
        return boardRepository.attachDelete(boardNo);
    }
}
