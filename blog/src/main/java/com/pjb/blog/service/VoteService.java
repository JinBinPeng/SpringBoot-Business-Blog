package com.pjb.blog.service;

import com.pjb.blog.domain.Vote;

/**
 * Vote 服务接口.
 */
public interface VoteService {
	/**
	 * 根据id获取 Vote
	 */
	Vote getVoteById(Long id);
	/**
	 * 删除Vote
	 */
	void removeVote(Long id);
}
