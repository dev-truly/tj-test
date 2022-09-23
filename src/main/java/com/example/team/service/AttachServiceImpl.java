package com.example.team.service;

import com.example.team.entity.BoardAttach;
import com.example.team.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachServiceImpl implements AttachService {
    @Autowired
    AttachRepository attachRepository;

    @Override
    public void deleteAttach(Long attachNo) {
        attachRepository.deleteById(attachNo);
    }

    @Override
    public List<BoardAttach> findByBoardNO(Long boardNo) {
        return attachRepository.findByBoardNo(boardNo);
    }


}
