package com.pjb.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单 值对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable{
	private String name;
	private String url;
}
