package com.example.team.repository;

import com.example.team.entity.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    int count(Specification<BoardEntity> spec);

    @Query(value = "Select max(ref) From board where board_code = :boardCode and `delete` = 'n'", nativeQuery = true)
    Long maxRefbyBoardCode(
            @Param("boardCode") String boardCode
    );

    @Modifying
    @Transactional
    @Query(value = "Update board Set `delete`='y' Where board_no = :boardNo", nativeQuery = true)
    int deleteBoard(@Param("boardNo") Long boardNo);

    @Modifying
    @Transactional
    @Query(value = "Update BoardEntity board Set board.attachCnt = board.attachCnt - 1 where board.boardNo = :boardNo")
    int attachDelete(@Param("boardNo") Long boardNo);

    List<BoardEntity> findAll(Specification<BoardEntity> spec, Pageable pageable);

    BoardEntity findByBoardNoAndDelete(Long boardNo, String delete);

    /*@Modifying
    @Query(value = "Select * from" +
            " board where" +
            " board.board_code = :boardCode and board.delete = 'n'" +
            " order by board.ref desc, board.step asc limit :to offset :from", nativeQuery = true)
    List<BoardEntity> findBoardList(
            @Param("boardCode") String boardCode,
            @Param("from") int from,
            @Param("to") int to
    );*/
}
