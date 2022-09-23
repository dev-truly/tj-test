package com.example.team.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "board")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;
    @Column(insertable=true, updatable=false)
    private String boardCode;
    private Long ref;
    @Column(insertable=false, updatable=false)
    private int depth;
    @Column(insertable=false, updatable=false)
    private int step;
    private String title;
    private String content;
    private String password;
    private String writer;
    @Column(insertable=true, updatable=false)
    private Long memberNo;
    private int attachCnt;
    @Column(insertable=false, updatable=true)
    private Date modifyDate;
    @Column(insertable=false, updatable=false)
    private int readCount;
    @Column(insertable=false, updatable=false)
    private String delete;
    @Column(insertable=true, updatable=false)
    private Date regDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "boardNo")
    private List<BoardAttach> boardAttaches = new ArrayList<>();
}
