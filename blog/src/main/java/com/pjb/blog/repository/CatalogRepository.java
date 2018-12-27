package com.pjb.blog.repository;

import com.pjb.blog.domain.Catalog;
import com.pjb.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Catalog 仓库.
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long>{
	
	/**
	 * 根据用户查询
	 */
	List<Catalog> findByUser(User user);
	
	/**
	 * 根据用户查询
	 */
	List<Catalog> findByUserAndName(User user, String name);
}
