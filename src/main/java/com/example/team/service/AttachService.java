package com.example.team.service;

import com.example.team.entity.BoardAttach;

import java.util.List;

public interface AttachService {
    public void deleteAttach(Long attachNo);

    public List<BoardAttach> findByBoardNO(Long boardNo);
}
