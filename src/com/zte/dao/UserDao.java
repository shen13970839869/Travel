package com.zte.dao;

import com.zte.pojo.User;

public interface UserDao {
	public User findByUsername(String username) ;
	 public void save(User user) ;
	 public User findByUsernameAndPassword(String username, String password) ;
	public User findByCode(String code);
	public void updateStatus(User user);
}
