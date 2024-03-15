package com.zte.service;

import com.zte.pojo.User;

public interface UserService {
	 public boolean regist(User user) ;
	 public User login(User user) ;
	public boolean active(String code);
}
