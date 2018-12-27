package com.pjb.blog.vo;


import com.pjb.blog.domain.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogVO implements Serializable {
	private String username;
	private Catalog catalog;
}
