package com.pjb.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Tag 值对象.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO implements Serializable {
	private String name;
	private Long count;
}
