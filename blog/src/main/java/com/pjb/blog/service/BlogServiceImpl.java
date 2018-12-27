package com.pjb.blog.service;

import com.pjb.blog.domain.*;
import com.pjb.blog.domain.es.EsBlog;
import com.pjb.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Blog 服务.
 */
@Service
public class BlogServiceImpl implements BlogService {

	private final BlogRepository blogRepository;
	private final EsBlogService esBlogService;

	@Autowired
	public BlogServiceImpl(BlogRepository blogRepository, EsBlogService esBlogService) {
		this.blogRepository = blogRepository;
		this.esBlogService = esBlogService;
	}

	@Transactional
	@Override
	public void saveBlog(Blog blog) {
		boolean isNew = (blog.getId() == null);
		EsBlog esBlog;
		
		Blog returnBlog = blogRepository.save(blog);
		
		if (isNew) {
			esBlog = new EsBlog(returnBlog);
		} else {
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog);
		}
		
		esBlogService.updateEsBlog(esBlog);
	}

	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogRepository.deleteById(id);
		EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}

	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		String tags = title;
		return blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,user, tags,user, pageable);
	}

	@Override
	public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		return blogRepository.findByUserAndTitleLike(user, title, pageable);
	}
	
	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		return blogRepository.findByCatalog(catalog, pageable);
	}

	@Override
	@Transactional
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if (blog != null) {
			blog.setReadSize(blog.getCommentSize()+1);
		}
		this.saveBlog(blog);
	}

	@Override
	@Transactional
	public void createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogRepository.findById(blogId).orElse(null);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Comment comment = new Comment(user, commentContent);
		if (originalBlog != null) {
			originalBlog.addComment(comment);
		}
		this.saveBlog(originalBlog);
	}

	@Override
	@Transactional
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogRepository.findById(blogId).orElse(null);
		if (originalBlog != null) {
			originalBlog.removeComment(commentId);
		}
		this.saveBlog(originalBlog);
	}

	@Override
	@Transactional
	public void createVote(Long blogId) {
		Blog originalBlog = blogRepository.findById(blogId).orElse(null);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Vote vote = new Vote(user);
		boolean isExist = false;
		if (originalBlog != null) {
			isExist = originalBlog.addVote(vote);
		}
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		this.saveBlog(originalBlog);
	}

	@Override
	@Transactional
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogRepository.findById(blogId).orElse(null);
		if (originalBlog != null) {
			originalBlog.removeVote(voteId);
		}
		this.saveBlog(originalBlog);
	}
}
