package com.pjb.blog.repository;

import com.pjb.blog.domain.Blog;
import com.pjb.blog.domain.Catalog;
import com.pjb.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Blog 仓库.
 */
public interface BlogRepository extends JpaRepository<Blog, Long>{
	/**
	 * 根据用户名分页查询用户列表
	 */
	Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
	
	/**
	 * 根据用户名分页查询用户列表
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);
	/**
	 * 根据用户名分页查询用户列表
	 */
	Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
}
