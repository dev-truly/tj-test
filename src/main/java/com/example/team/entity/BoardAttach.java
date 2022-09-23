package com.example.team.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardAttach {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachNo;
	private Long boardNo;
	private String oriFileName;
	private String serverFileName;
	private Date regDate;
}
