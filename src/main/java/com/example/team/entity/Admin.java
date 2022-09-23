package com.example.team.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

// Member 테이블의 데이터를 저장하는 VO클래스

/*** 문서 정보를 저장하는 주석
 *  @author instructor
 */
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AdminNo;
	private String adminId;
	private String password;
	private String adminName;
	@Column(insertable=false, updatable=true)
	private String useFlag;
	private String tel;
	@Column(insertable=false, updatable=false)
	private Date regDate;
}
