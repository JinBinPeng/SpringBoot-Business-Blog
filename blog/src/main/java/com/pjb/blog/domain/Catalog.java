package com.pjb.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Catalog 实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Catalog implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "名称不能为空")
	@Size(min=2, max=30)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String name;
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	
	public Catalog(User user, String name) {
		this.name = name;
		this.user = user;
	}
}
