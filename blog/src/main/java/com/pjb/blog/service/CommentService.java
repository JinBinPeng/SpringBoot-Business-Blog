package com.pjb.blog.service;

import com.pjb.blog.domain.Comment;

/**
 * Comment 服务接口.
 */
public interface CommentService {
	/**
	 * 根据id获取 Comment
	 */
	Comment getCommentById(Long id);
	/**
	 * 删除评论
	 */
	void removeComment(Long id);
}
