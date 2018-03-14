package com.acc.dao;

import com.acc.dto.User;

public interface LoginDao {

	public User checkUser(String username);
	
}
