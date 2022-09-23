package com.example.team.repository;

import com.example.team.entity.BoardAttach;
import com.example.team.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<BoardAttach, Long> {
    List<BoardAttach> findByBoardNo(Long boardNo);
}
