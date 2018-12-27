package com.pjb.blog.service;

import com.pjb.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * User 服务接口.
 */
public interface UserService {
	/**
	 * 保存用户
	 */
	User saveUser(User user);
	
	/**
	 * 删除用户
	 */
	void removeUser(Long id);
	
	/**
	 * 删除列表里面的用户
	 */
	void removeUsersInBatch(List<User> users);
	
	/**
	 * 更新用户
	 */
	User updateUser(User user);
	
	/**
	 * 根据id获取用户
	 */
	User getUserById(Long id);
	
	/**
	 * 获取用户列表
	 */
	List<User> listUsers();
	
	/**
	 * 根据用户名进行分页模糊查询
	 */
	Page<User> listUsersByNameLike(String name, Pageable pageable);
	
	/**
	 * 更具名称列表查询
	 */
	List<User> listUsersByUsernames(Collection<String> usernames);
}
